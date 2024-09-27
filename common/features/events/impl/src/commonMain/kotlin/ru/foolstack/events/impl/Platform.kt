package ru.foolstack.events.impl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform