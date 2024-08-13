package ru.foolstack.storage.impl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform