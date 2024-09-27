package ru.foolstack.study.api

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform