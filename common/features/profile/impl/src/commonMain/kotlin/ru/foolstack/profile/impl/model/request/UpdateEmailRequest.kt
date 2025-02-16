package ru.foolstack.profile.impl.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateEmailRequest(
    @SerialName("email") val email: String
)