package com.src.taipei_travel.data.remote.model

data class AttractionsResponse(
    val `data`: List<Attraction>,
    val total: Int
)