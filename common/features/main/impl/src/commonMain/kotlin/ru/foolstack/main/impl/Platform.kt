package ru.foolstack.main.impl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform