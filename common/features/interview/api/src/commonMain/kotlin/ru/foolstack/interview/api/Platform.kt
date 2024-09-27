package ru.foolstack.interview.api

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform