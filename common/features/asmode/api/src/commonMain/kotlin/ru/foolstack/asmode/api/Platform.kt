package ru.foolstack.asmode.api

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform