package ru.foolstack.settings.api

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform