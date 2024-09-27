package ru.foolstack.authorization.impl.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthByEmailResponse(
    @SerialName("userToken") val userToken: String = "",
    @SerialName("userRefreshToken") val userRefreshToken: String = "",
    @SerialName("errorMsg") val errorMsg: String
)