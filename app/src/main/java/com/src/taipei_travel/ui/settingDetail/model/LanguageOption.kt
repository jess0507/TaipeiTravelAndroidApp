package com.src.taipei_travel.ui.settingDetail.model

import com.src.taipei_travel.data.local.datastore.model.Language

class LanguageOption(val language: Language): Option(language.name) {
    companion object {
        fun getAllOptions(): List<LanguageOption> = Language.entries.map { LanguageOption(it) }
    }
}