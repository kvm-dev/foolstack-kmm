package ru.foolstack.authorization.impl.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthByEmailRequest(
    @SerialName("email") val email: String,
    @SerialName("code") val code: String
)