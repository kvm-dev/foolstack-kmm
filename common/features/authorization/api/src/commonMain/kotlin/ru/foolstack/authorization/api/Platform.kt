package ru.foolstack.authorization.api

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform