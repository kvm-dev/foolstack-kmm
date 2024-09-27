package ru.foolstack.storage

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform