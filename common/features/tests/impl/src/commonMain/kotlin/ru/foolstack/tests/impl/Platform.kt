package ru.foolstack.tests.impl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform