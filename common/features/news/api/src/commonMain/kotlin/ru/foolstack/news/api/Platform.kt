package ru.foolstack.news.api

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform