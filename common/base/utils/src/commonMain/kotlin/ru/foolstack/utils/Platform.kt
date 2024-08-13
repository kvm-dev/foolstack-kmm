package ru.foolstack.utils

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform