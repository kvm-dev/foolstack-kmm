package ru.foolstack.tests.api.model

data class SendResultDomain(
    val success: Boolean,
    val errorMsg: String
)