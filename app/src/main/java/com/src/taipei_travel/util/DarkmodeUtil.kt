package com.src.taipei_travel.util

import android.content.Context
import android.content.res.Configuration

object DarkModeUtil {
    fun isDarkMode(context: Context): Boolean {
        val currentNightMode =
            context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }
}