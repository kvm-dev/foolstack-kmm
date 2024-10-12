package ru.foolstack.news.impl.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.foolstack.news.api.domain.usecase.GetNewsUseCase
import ru.foolstack.news.api.model.NewsDomain
import ru.foolstack.news.impl.data.repository.NewsRepository
import ru.foolstack.utils.model.ResultState

class GetNewsUseCaseImpl(private val repository: NewsRepository):GetNewsUseCase {

    private val _news = MutableStateFlow<ResultState<NewsDomain>>(
        ResultState.Idle)
    override val newsState = _news.asStateFlow()
    override suspend fun getNews(fromLocal:Boolean): NewsDomain {
        _news.tryEmit(ResultState.Loading)
        return if(fromLocal){
            val cachedNews = repository.getNewsFromLocal()
            _news.tryEmit(ResultState.Success(cachedNews))
            cachedNews
        }
        else{
            val responseNews = repository.getNewsFromServer()
            _news.tryEmit(ResultState.Success(responseNews))
            responseNews
        }
    }
}