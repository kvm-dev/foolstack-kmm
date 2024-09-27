package ru.foolstack.authorization.api.model

data class ConfirmAuthAndRegDomain(
    val success: Boolean,
    val userToken: String,
    val userRefreshToken: String,
    val errorMsg: String
)