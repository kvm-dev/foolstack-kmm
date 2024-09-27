package ru.foolstack.books.impl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform