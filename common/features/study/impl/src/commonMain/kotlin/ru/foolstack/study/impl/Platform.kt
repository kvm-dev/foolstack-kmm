package ru.foolstack.study.impl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform