package ru.foolstack.ui.model

data class NewsItem(
    val newsId: Int,
    val newsName: String,
    val newsText: String,
    val newsDate: Long,
    val newsLink: String,
    val newsImageBase64: String
)