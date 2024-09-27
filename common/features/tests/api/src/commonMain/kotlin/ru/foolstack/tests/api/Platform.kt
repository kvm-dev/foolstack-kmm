package ru.foolstack.tests.api

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform