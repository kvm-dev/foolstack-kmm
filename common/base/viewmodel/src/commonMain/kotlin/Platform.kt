package ru.foolstack.viewmodel

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform