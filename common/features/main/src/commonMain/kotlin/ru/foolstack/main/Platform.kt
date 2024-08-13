package ru.foolstack.main

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform