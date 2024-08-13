package ru.foolstack.authorization.impl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform