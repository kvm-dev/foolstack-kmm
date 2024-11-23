package ru.foolstack.comments.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class MaterialCommentRequest(
    @SerialName("materialId") val materialId: Int,
    @SerialName("comment") val comment: String
)