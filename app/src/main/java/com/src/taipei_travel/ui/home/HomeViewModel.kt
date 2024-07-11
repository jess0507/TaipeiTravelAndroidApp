package com.src.taipei_travel.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.src.taipei_travel.data.local.datastore.model.Language
import com.src.taipei_travel.domain.DataRepositoryImpl
import com.src.taipei_travel.data.remote.Result
import com.src.taipei_travel.data.remote.model.Attraction
import com.src.taipei_travel.data.remote.model.New
import com.src.taipei_travel.ui.settingDetail.SettingDetailState
import com.src.taipei_travel.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataRepo: DataRepositoryImpl
): ViewModel() {
    var attractions = MutableStateFlow<List<Attraction>>(listOf())
    var news = MutableStateFlow<List<New>>(listOf())
    val homeCategoryState : StateFlow<HomeCategoryState> = combine(
        attractions,
        news
    ) { attractions, news ->
        HomeCategoryState(
            listOf(
                HomeCategory(HomeItem.converter(news)),
                HomeCategory(HomeItem.converter(attractions)),
            )
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        HomeCategoryState(listOf())
    )

    var showProgressBar = MutableLiveData(false)
    var errorMsg = MutableLiveData("")

    private var isLoading = false
    private var language: Language = Language.English
    private var page: Int = 1

    init {
        Timber.d("initial")
    }

    private suspend fun fetchNews() {
        Timber.d("fetchNews lang:$language, page: $page, size: ${news.value.size}")
        isLoading = true
        showProgressBar.value = true
        dataRepo.fetchNews(language.code, page).let {
            when(it) {
                is Result.Success -> {
                    Timber.d("finish fetchNews size: ${news.value.size}")
                    if (page == Constants.INITIAL_PAGE) {
                        news.value = it.data
                    } else {
                        news.value = news.value.plus(it.data)
                    }
                    showProgressBar.value = false
                    isLoading = false
                }
                is Result.Error -> {
                    errorMsg.value = it.toString()
                    showProgressBar.value = false
                    isLoading = false
                }
                is Result.Loading -> {}
            }
        }
    }

    private suspend fun fetchAttractions() {
        Timber.d("fetchAttractions lang: $language, page: $page")
        isLoading = true
        showProgressBar.value = true
        dataRepo.fetchAttractions(language.code, page).let {
            when(it) {
                is Result.Success -> {
                    Timber.d("finish fetchAttractions size: ${attractions.value.size}")
                    if (page == Constants.INITIAL_PAGE) {
                        attractions.value = it.data
                    } else {
                        attractions.value = attractions.value.plus(it.data)
                    }
                    showProgressBar.value = false
                    isLoading = false
                }
                is Result.Error -> {
                    errorMsg.value = it.toString()
                    showProgressBar.value = false
                    isLoading = false
                }
                is Result.Loading -> {}
            }
        }
    }

    fun initialList(lang: Language) {
        Timber.d("initialList")
        if (isLoading || lang == language && !attractions.value.isNullOrEmpty()) return
        page = 1
        language = lang
        viewModelScope.launch {
            fetchNews()
            fetchAttractions()
        }
    }

    fun loadMoreList() {
        Timber.d("loadList, showProcessBar: ${showProgressBar.value}")
        if (isLoading || attractions.value.isNullOrEmpty()) return
        page ++
        viewModelScope.launch {
            fetchAttractions()
        }
    }
}