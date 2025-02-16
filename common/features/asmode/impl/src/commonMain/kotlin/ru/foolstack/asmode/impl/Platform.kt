package ru.foolstack.asmode.impl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform