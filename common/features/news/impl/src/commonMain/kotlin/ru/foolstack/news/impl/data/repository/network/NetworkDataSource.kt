package ru.foolstack.news.impl.data.repository.network

import ru.foolstack.news.api.model.SingleNewsDomain
import ru.foolstack.news.api.model.NewsDomain
import ru.foolstack.news.impl.mapper.Mapper
import ru.foolstack.news.impl.model.NewsVersionResponse

class NetworkDataSource(private val api: NewsApi, private val mapper: Mapper){

    suspend fun getNews(): NewsDomain {
        val response = api.getNews()
        val newsList = ArrayList<SingleNewsDomain>()
        response.news.forEach { new->
            newsList.add(mapper.map(new))
        }
        return NewsDomain(
            news = newsList,
            errorMsg = response.errorMsg
        )
    }

    suspend fun getVersion():NewsVersionResponse{
        return api.getVersion()
    }
}