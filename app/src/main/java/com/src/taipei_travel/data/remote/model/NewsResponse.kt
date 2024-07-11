package com.src.taipei_travel.data.remote.model

data class NewsResponse(
    val `data`: List<New>,
    val total: Int
)