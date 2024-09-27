package ru.foolstack.profile.impl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform