package ru.foolstack.comments.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class MaterialCommentResponse(
    @SerialName("success") val success: Boolean = false,
    @SerialName("errorMsg") val errorMsg: String
)