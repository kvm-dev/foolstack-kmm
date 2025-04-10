package ru.foolstack.splash.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LastVersionResponse(
    @SerialName("lastVersion") val lastVersion: String = "",
    @SerialName("aboutUpdate") val aboutUpdate: String = "",
    @SerialName("isImportant") val isImportant: Boolean = false,
    @SerialName("errorMsg") val errorMsg: String = ""
)