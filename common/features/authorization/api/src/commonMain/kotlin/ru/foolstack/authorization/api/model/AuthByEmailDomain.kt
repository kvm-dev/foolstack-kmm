package ru.foolstack.authorization.api.model

data class AuthByEmailDomain(
    val userToken: String,
    val userRefreshToken: String,
    val errorMsg: String
)