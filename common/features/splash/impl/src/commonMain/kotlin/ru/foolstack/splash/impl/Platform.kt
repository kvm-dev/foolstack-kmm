package ru.foolstack.splash.impl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform