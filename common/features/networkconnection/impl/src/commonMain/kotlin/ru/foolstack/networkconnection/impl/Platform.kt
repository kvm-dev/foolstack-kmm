package ru.foolstack.networkconnection.impl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform