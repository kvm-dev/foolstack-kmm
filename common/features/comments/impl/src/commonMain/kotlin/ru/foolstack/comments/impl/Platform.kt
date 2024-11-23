package ru.foolstack.comments.impl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform