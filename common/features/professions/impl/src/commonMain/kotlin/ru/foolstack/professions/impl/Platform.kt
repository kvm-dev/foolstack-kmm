package ru.foolstack.professions.impl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform