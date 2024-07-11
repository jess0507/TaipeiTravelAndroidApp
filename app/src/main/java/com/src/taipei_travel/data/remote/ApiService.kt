package com.src.taipei_travel.data.remote

import com.src.taipei_travel.data.remote.model.AttractionsResponse
import com.src.taipei_travel.data.remote.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("open-api/{lang}/Attractions/All")
    suspend fun fetchAttractions(
        @Path("lang") userName: String,
        @Query("page") page: Int = 0,
    ): AttractionsResponse

    @GET("open-api/{lang}/Events/News")
    suspend fun fetchNews(
        @Path("lang") userName: String,
        @Query("page") page: Int = 0,
    ): NewsResponse

    companion object {
        const val CONNECT_TIME_OUT = 30L
        const val READ_TIME_OUT = 30L
        const val WRITE_TIME_OUT = 30L
    }
}
