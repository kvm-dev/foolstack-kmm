package ru.foolstack.news.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsVersionResponse(
    @SerialName("success") val success: Boolean = false,
    @SerialName("version") val version: Int = 0,
    @SerialName("errorMsg") val errorMsg: String
)