package com.src.taipei_travel.repository

import com.src.taipei_travel.data.remote.ApiService
import com.src.taipei_travel.data.remote.Result
import com.src.taipei_travel.data.remote.Result.Error
import com.src.taipei_travel.data.remote.Result.Success
import com.src.taipei_travel.data.remote.model.Attraction
import com.src.taipei_travel.data.remote.model.AttractionsResponse
import com.src.taipei_travel.data.remote.model.New
import com.src.taipei_travel.data.remote.model.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(private val apiService: ApiService): DataRepository {
    override suspend fun fetchAttractions(lang: String, page: Int): Result<List<Attraction>> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val attractionsResponse: AttractionsResponse = apiService.fetchAttractions(lang, page)
                Success(attractionsResponse.data)
            } catch (e: Exception) {
                Error(e)
            }
        }
    }

    override suspend fun fetchNews(lang: String, page: Int): Result<List<New>> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val attractionsResponse: NewsResponse = apiService.fetchNews(lang, page)
                Success(attractionsResponse.data)
            } catch (e: Exception) {
                Error(e)
            }
        }
    }
}