package ru.foolstack.news.api.domain.usecase

import ru.foolstack.news.api.model.NewsDomain

interface GetNewsUseCase {

    suspend fun getNews(fromLocal: Boolean = false):NewsDomain
}