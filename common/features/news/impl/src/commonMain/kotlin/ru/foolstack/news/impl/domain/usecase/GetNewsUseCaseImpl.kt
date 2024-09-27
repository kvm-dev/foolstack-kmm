package ru.foolstack.news.impl.domain.usecase

import ru.foolstack.news.api.domain.usecase.GetNewsUseCase
import ru.foolstack.news.api.model.NewsDomain
import ru.foolstack.news.impl.data.repository.NewsRepository

class GetNewsUseCaseImpl(private val repository: NewsRepository):GetNewsUseCase {
    override suspend fun getNews(fromLocal:Boolean): NewsDomain {
        return if(fromLocal){
            repository.getNewsFromLocal()
        }
        else{
            repository.getNewsFromServer()
        }
    }
}