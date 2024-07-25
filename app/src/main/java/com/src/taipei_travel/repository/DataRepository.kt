package com.src.taipei_travel.repository

import com.src.taipei_travel.data.remote.Result
import com.src.taipei_travel.data.remote.model.Attraction
import com.src.taipei_travel.data.remote.model.New

interface DataRepository {
    suspend fun fetchAttractions(lang: String, page: Int): Result<List<Attraction>>
    suspend fun fetchNews(lang: String, page: Int): Result<List<New>>
}