package ru.foolstack.ui

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform