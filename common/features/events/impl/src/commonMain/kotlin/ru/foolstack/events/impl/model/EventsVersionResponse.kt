package ru.foolstack.events.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventsVersionResponse(
    @SerialName("success") val success: Boolean = false,
    @SerialName("version") val version: Int = 0,
    @SerialName("errorMsg") val errorMsg: String
)