package ru.foolstack.news.impl.data.repository.network

object NewsEndpoints {
    val getNews: String
        get() = "news/get-news/"

    val getNewsVersion: String
        get() = "news/get-version/"
}