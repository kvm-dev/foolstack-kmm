package ru.foolstack.networkconnection.api

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform