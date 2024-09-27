package ru.foolstack.registration.impl.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationByEmailRequest(
    @SerialName("email") val email: String
)