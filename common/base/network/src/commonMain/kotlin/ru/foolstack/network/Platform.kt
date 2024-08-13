package ru.foolstack.network

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform