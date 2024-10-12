package ru.foolstack.professions.api

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform