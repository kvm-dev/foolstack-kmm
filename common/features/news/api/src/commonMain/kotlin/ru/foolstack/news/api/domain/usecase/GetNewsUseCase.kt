package ru.foolstack.news.api.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import ru.foolstack.news.api.model.NewsDomain
import ru.foolstack.utils.model.ResultState

interface GetNewsUseCase {

    val newsState: StateFlow<ResultState<NewsDomain>>
    suspend fun getNews(fromLocal: Boolean = false):NewsDomain
}