package ru.foolstack.news.impl.data.repository

import ru.foolstack.news.api.model.NewsDomain
import ru.foolstack.news.impl.data.repository.local.LocalDataSource
import ru.foolstack.news.impl.data.repository.network.NetworkDataSource

class NewsRepository(private val networkDataSource: NetworkDataSource, private val localDataSource: LocalDataSource) {
    suspend fun getNewsFromServer(): NewsDomain {
        val currentVersion = localDataSource.getNewsVersion()
        val serverVersion = networkDataSource.getVersion().version
        return if(currentVersion != serverVersion){
            val result = networkDataSource.getNews()
            if(result.errorMsg.isNotEmpty()){
                result
            }
            else{
                localDataSource.updateNewsVersion(serverVersion)
                localDataSource.saveNews(result)
                localDataSource.getNews()
            }
        } else{
            localDataSource.getNews()
        }
    }

    suspend fun getNewsFromLocal(): NewsDomain = localDataSource.getNews()
}