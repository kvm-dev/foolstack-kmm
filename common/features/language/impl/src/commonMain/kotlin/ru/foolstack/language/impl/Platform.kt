package ru.foolstack.language.impl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform