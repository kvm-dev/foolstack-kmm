package ru.foolstack.registration.api

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform