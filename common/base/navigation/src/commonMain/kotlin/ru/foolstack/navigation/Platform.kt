package ru.foolstack.navigation

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform