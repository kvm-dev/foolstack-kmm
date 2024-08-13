package ru.foolstack.notifications

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform