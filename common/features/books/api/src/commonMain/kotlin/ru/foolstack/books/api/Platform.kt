package ru.foolstack.books.api

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform