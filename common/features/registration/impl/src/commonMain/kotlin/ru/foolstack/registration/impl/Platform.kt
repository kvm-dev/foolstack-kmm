package ru.foolstack.registration.impl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform