package ru.foolstack.network.model

sealed class UserType(val key: String, val type: String) {
    class Client : UserType("Usertype","client")
    class Guest : UserType("Usertype","guest")
    class Test : UserType("Usertype","test")
}