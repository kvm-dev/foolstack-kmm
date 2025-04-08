package ru.foolstack.news.api.model

data class NewsDomain(
    val news: List<SingleNewsDomain>,
    val errorMsg: String
)

data class SingleNewsDomain(
    val newsId: Int,
    val newsName: String,
    val newsText: String,
    val newsDate: Long,
    val newsImageBase64: String,
    val newsLink: String
)