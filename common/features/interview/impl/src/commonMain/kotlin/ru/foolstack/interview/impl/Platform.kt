package ru.foolstack.interview.impl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform