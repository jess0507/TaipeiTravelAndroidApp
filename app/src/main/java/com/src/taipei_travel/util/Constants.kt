package com.src.taipei_travel.util

import com.src.taipei_travel.data.model.Language
import com.google.gson.Gson

object Constants {
    const val INITIAL_PAGE = 1

    fun getTranslation(language: Language): Map<String, String> {
        val translations = when(language) {
            Language.English -> """
                {
                  "home_title": "Travel Taipei",
                  "new_title": "News",
                  "setting_title": "Setting",
                  "Language": "Language",
                  "DarkMode": "DarkMode",
                  "home": "Home",
                  "setting": "Setting",
                   "English": "English",
                   "TraditionalChinese": "TraditionalChinese",
                   "SimplifiedChinese": "TraditionalChinese",
                   "Japanese": "Japanese",
                   "Korean": "Korean",
                   "Spanish": "Spanish",
                   "Indonesian": "Indonesian",
                   "Thai": "Thai",
                   "Vietnamese": "Vietnamese",
                   "Light": "Light Mode",
                   "Dark": "Dark Mode",
                   "System": "System Mode",
                   "new_list_title": "News",
                   "attraction_list_title": "Attractions"
                }
            """
            Language.TraditionalChinese -> """
                {
                  "home_title": "台北旅游",
                  "setting_title": "設定",
                  "Language": "語言",
                  "DarkMode": "深色主題",
                  "home": "首頁",
                  "setting": "設定",
                   "English": "英文",
                   "TraditionalChinese": "繁體中文",
                   "SimplifiedChinese": "簡體中文",
                   "Japanese": "日文",
                   "Korean": "韓文",
                   "Spanish": "西班牙文",
                   "Indonesian": "印尼文",
                   "Thai": "泰文",
                   "Vietnamese": "越南文",
                   "Light": "明亮主題",
                   "Dark": "深色主題",
                   "System": "系統主題",
                   "new_list_title": "最新消息",
                   "attraction_list_title": "遊憩景點"
                }
            """
            Language.SimplifiedChinese -> """
                {
                  "home_title": "台北旅游",
                  "new_title": "消息",
                  "setting_title": "环境",
                  "Language": "語言",
                  "DarkMode": "深色主題",
                  "home": "首頁",
                  "setting": "設定",
                   "English": "英文",
                   "TraditionalChinese": "繁體中文",
                   "SimplifiedChinese": "簡體中文",
                   "Japanese": "日文",
                   "Korean": "韓文",
                   "Spanish": "西班牙文",
                   "Indonesian": "印尼文",
                   "Thai": "泰文",
                   "Vietnamese": "越南文",
                   "Light": "明亮主題",
                   "Dark": "深色主題",
                   "System": "系統主題",
                   "new_list_title": "最新消息",
                   "attraction_list_title": "遊憩景點"
                }
            """
            Language.Japanese -> """
                {
                  "home_title": "トラベル台北",
                  "new_title": "ニュース",
                  "setting_title": "設定",
                  "Language": "語言",
                  "DarkMode": "深色主題",
                  "home": "首頁",
                  "setting": "設定",
                   "English": "英文",
                   "TraditionalChinese": "繁體中文",
                   "SimplifiedChinese": "簡體中文",
                   "Japanese": "日文",
                   "Korean": "韓文",
                   "Spanish": "西班牙文",
                   "Indonesian": "印尼文",
                   "Thai": "泰文",
                   "Vietnamese": "越南文",
                   "Light": "明亮主題",
                   "Dark": "深色主題",
                   "System": "系統主題",
                   "new_list_title": "最新消息",
                   "attraction_list_title": "遊憩景點"
                }
            """
            Language.Korean -> """
                {
                  "home_title": "타이페이 여행",
                  "new_title": "소식",
                  "setting_title": "환경",
                  "Language": "언어",
                  "DarkMode": "深色主題",
                  "home": "首頁",
                  "setting": "設定",
                   "English": "英文",
                   "TraditionalChinese": "繁體中文",
                   "SimplifiedChinese": "簡體中文",
                   "Japanese": "日文",
                   "Korean": "韓文",
                   "Spanish": "西班牙文",
                   "Indonesian": "印尼文",
                   "Thai": "泰文",
                   "Light": "明亮主題",
                   "Dark": "深色主題",
                   "System": "系統主題",
                   "system_mode": "系統主題",
                   "new_list_title": "最新消息",
                   "attraction_list_title": "遊憩景點"
                }
            """
            Language.Spanish -> """
                {
                  "home_title": "Viajes Taipéi",
                  "new_title": "Noticias",
                  "setting_title": "Configuración",
                  "Language": "Idioma",
                  "DarkMode": "深色主題",
                  "home": "首頁",
                  "setting": "設定",
                   "English": "英文",
                   "TraditionalChinese": "繁體中文",
                   "SimplifiedChinese": "簡體中文",
                   "Japanese": "日文",
                   "Korean": "韓文",
                   "Spanish": "西班牙文",
                   "Indonesian": "印尼文",
                   "Thai": "泰文",
                   "Vietnamese": "越南文",
                   "Light": "明亮主題",
                   "Dark": "深色主題",
                   "System": "系統主題",
                   "new_list_title": "最新消息",
                   "attraction_list_title": "遊憩景點"
                }
            """
            Language.Indonesian -> """
                {
                  "home_title": "Perjalanan Taipei",
                  "new_title": "Berita",
                  "setting_title": "Pengaturan",
                  "Language": "Bahasa",
                  "DarkMode": "深色主題",
                  "home": "首頁",
                  "setting": "設定",
                   "English": "英文",
                   "TraditionalChinese": "繁體中文",
                   "SimplifiedChinese": "簡體中文",
                   "Japanese": "日文",
                   "Korean": "韓文",
                   "Spanish": "西班牙文",
                   "Indonesian": "印尼文",
                   "Thai": "泰文",
                   "Vietnamese": "越南文",
                   "Light": "明亮主題",
                   "Dark": "深色主題",
                   "System": "系統主題",
                   "new_list_title": "最新消息",
                   "attraction_list_title": "遊憩景點"
                }
            """
            Language.Thai -> """
                {
                  "home_title": "เที่ยวไทเป",
                  "new_title": "ข่าว",
                  "setting_title": "การตั้งค่า",
                  "Language": "ภาษา",
                  "DarkMode": "深色主題",
                  "home": "首頁",
                  "setting": "設定",
                   "English": "英文",
                   "TraditionalChinese": "繁體中文",
                   "SimplifiedChinese": "簡體中文",
                   "Japanese": "日文",
                   "Korean": "韓文",
                   "Spanish": "西班牙文",
                   "Indonesian": "印尼文",
                   "Thai": "泰文",
                   "Vietnamese": "越南文",
                   "Light": "明亮主題",
                   "Dark": "深色主題",
                   "System": "系統主題",
                   "new_list_title": "最新消息",
                   "attraction_list_title": "遊憩景點"
                }
            """
            Language.Vietnamese -> """
                {
                  "home_title": "Du lịch Đài Bắc",
                  "new_title": "Tin tức",
                  "setting_title": "Cài đặt",
                  "Language": "Ngôn ngữ",
                  "DarkMode": "深色主題",
                  "home": "首頁",
                  "setting": "設定",
                   "English": "英文",
                   "TraditionalChinese": "繁體中文",
                   "SimplifiedChinese": "簡體中文",
                   "Japanese": "日文",
                   "Korean": "韓文",
                   "Spanish": "西班牙文",
                   "Indonesian": "印尼文",
                   "Thai": "泰文",
                   "Vietnamese": "越南文",
                   "Light": "明亮主題",
                   "Dark": "深色主題",
                   "System": "系統主題",
                   "new_list_title": "最新消息",
                   "attraction_list_title": "遊憩景點"
                }
            """
        }

        return Gson().fromJson(translations.trimIndent(), Map::class.java) as Map<String, String>
    }

}