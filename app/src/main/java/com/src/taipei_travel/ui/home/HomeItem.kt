package com.src.taipei_travel.ui.home

import com.src.taipei_travel.data.remote.model.Attraction
import com.src.taipei_travel.data.remote.model.New

sealed class HomeItem {
    data class NewItem(val new: New): HomeItem()
    data class AttractionItem(val attraction: Attraction): HomeItem()

    val name: String =
        when (this) {
            is NewItem -> "new_list_title"
            is AttractionItem -> "attraction_list_title"
        }

    companion object {
        fun converter(new: New) = NewItem(new)
        fun converter(attraction: Attraction) = AttractionItem(attraction)

        fun <T> converter(items: List<T>): List<HomeItem> {
            return items.map {
                when (it) {
                    is New -> NewItem(it)
                    is Attraction -> AttractionItem(it)
                    else -> throw IllegalArgumentException("Unknown type")
                }
            }
        }
    }
}