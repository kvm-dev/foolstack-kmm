package ru.foolstack.splash.api

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform