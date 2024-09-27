package ru.foolstack.news.impl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform