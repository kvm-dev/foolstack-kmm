package ru.foolstack.news.impl.data.repository

import ru.foolstack.news.api.model.NewsDomain
import ru.foolstack.news.impl.data.repository.local.LocalDataSource
import ru.foolstack.news.impl.data.repository.network.NetworkDataSource

class NewsRepository(private val networkDataSource: NetworkDataSource, private val localDataSource: LocalDataSource) {
    suspend fun getNewsFromServer(): NewsDomain {
        val result = networkDataSource.getNews()
        return if(result.errorMsg.isNotEmpty()){
                result
        }
        else{
            localDataSource.saveNews(result)
            localDataSource.getNews()
        }
    }

    suspend fun getNewsFromLocal(): NewsDomain = localDataSource.getNews()
}