package ru.foolstack.news.impl.data.repository.network

import ru.foolstack.network.utils.getBase64Bitmap
import ru.foolstack.news.api.model.NewDomain
import ru.foolstack.news.api.model.NewsDomain
import ru.foolstack.news.impl.mapper.Mapper

class NetworkDataSource(private val api: NewsApi, private val mapper: Mapper){

    suspend fun getNews(): NewsDomain {
        val response = api.getNews()
        val newsList = ArrayList<NewDomain>()
        response.news.forEach { new->
            var newsImageBase64 = ""
            if(new.newsImageUrl.isNotEmpty()){
                newsImageBase64 = getBase64Bitmap(new.newsImageUrl)
            }
            newsList.add(mapper.map(new, newsImageBase64))
        }
        return NewsDomain(
            news = newsList,
            errorMsg = response.errorMsg
        )
    }
}