package ru.foolstack.news.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    @SerialName("news") val news: List<NewResponse> = listOf(),
    @SerialName("errorMsg") val errorMsg: String
)
@Serializable
data class NewResponse(
    @SerialName("newsId")val newsId: Int = 0,
    @SerialName("newsName") val newsName: String = "",
    @SerialName("newsText") val newsText: String = "",
    @SerialName("newsLink") val newsLink: String = "",
    @SerialName("newsDate") val newsDate: Long = 0L,
    @SerialName("newsImage") val newsImage: String = ""
)