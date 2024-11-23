package ru.foolstack.comments.api

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform