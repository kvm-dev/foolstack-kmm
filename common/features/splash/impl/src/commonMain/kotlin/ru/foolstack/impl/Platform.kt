package ru.foolstack.impl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform