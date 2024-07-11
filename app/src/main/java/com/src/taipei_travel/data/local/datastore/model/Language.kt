package com.src.taipei_travel.data.local.datastore.model

import kotlinx.serialization.Serializable

@Serializable
enum class Language(val code: String) {
    English("en"),
    TraditionalChinese("zh-tw"),
    SimplifiedChinese("zh-cn"),
    Japanese("ja"),
    Korean("ko"),
    Spanish("es"),
    Indonesian("id"),
    Thai("th"),
    Vietnamese("vi");
}