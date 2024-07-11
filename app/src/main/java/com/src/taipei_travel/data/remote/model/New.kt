package com.src.taipei_travel.data.remote.model

data class New(
    val begin: Any,
    val description: String,
    val end: Any,
    val files: List<Any>,
    val id: Int,
    val links: List<Any>,
    val modified: String,
    val posted: String,
    val title: String,
    val url: String
)