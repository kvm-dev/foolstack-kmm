package ru.foolstack.profile.impl.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateNameRequest(
    @SerialName("name") val name: String
)