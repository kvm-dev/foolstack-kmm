package ru.foolstack.foolstack

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform