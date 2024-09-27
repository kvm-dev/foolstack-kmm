package ru.foolstack.profile.api

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform