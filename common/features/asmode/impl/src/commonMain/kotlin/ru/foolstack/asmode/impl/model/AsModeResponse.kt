package ru.foolstack.asmode.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AsModeResponse(
    @SerialName("isAsModeActive") val isAsModeActive: Boolean = false,
    @SerialName("errorMsg") val errorMsg: String = ""
)