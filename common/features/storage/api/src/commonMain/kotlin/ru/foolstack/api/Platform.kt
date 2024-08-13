package ru.foolstack.api

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform