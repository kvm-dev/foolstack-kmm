package ru.foolstack.settings.impl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform