package com.src.taipei_travel.data.model

class Option(val name: String)

fun Setting.toOption(): Option {
    return Option(name = this.name)
}

fun Option.toSetting(): Setting? {
    return Setting.entries.firstOrNull { it.name == this.name }
}

fun Language.toOption(): Option {
    return Option(name = this.name)
}

fun Option.toLanguage(): Language? {
    return Language.entries.firstOrNull { it.name == this.name }
}

fun DarkMode.toOption(): Option {
    return Option(name = this.name)
}

fun Option.toDarkMode(): DarkMode? {
    return DarkMode.entries.firstOrNull { it.name == this.name }
}