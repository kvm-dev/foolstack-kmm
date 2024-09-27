package ru.foolstack.main.api

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform