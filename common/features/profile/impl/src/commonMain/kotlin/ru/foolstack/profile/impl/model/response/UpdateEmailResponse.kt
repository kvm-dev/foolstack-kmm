package ru.foolstack.profile.impl.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateEmailResponse (
    @SerialName("success") val success: Boolean = false,
    @SerialName("errorMsg") val errorMsg: String = ""
)