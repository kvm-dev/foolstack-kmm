package ru.foolstack.language.api

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform