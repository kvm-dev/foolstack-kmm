package ru.foolstack.authorization.api.model


data class IsUserExistDomain(
    val success: Boolean,
    val errorMsg: String
)