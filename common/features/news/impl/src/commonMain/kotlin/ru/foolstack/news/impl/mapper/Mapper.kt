package ru.foolstack.news.impl.mapper

import ru.foolstack.news.api.model.NewDomain
import ru.foolstack.news.api.model.NewsDomain
import ru.foolstack.news.impl.model.NewResponse
import ru.foolstack.news.impl.model.NewsResponse
import ru.foolstack.storage.model.New
import ru.foolstack.storage.model.News

class Mapper {

     fun map(response:NewResponse, base64Image: String):NewDomain{
                return NewDomain(
                    newsId = response.newsId,
                    newsName = response.newsName,
                    newsText = response.newsText,
                    newsDate = response.newsDate,
                    newsLink = response.newsLink,
                    newsImageUrl = response.newsImageUrl,
                    newsImageBase64 = base64Image)
        }

    fun map(response: News):NewsDomain{
        val newsList = ArrayList<NewDomain>()
        response.news.forEach { new->
            newsList.add(
                NewDomain(
                newsId = new.newsId,
                newsName = new.newsName,
                newsDate = new.newsDate,
                newsText = new.newsText,
                newsLink = new.newsLink,
                newsImageUrl = new.newsImageUrl,
                newsImageBase64 = new.newsImageBase64
            )
            )
        }
        return NewsDomain(
            news = newsList,
            errorMsg = response.errorMsg
        )
    }

    fun map(response: NewsDomain):News{
        val newsList = ArrayList<New>()
        response.news.forEach { new->
            newsList.add(New(
                newsId = new.newsId,
                newsName = new.newsName,
                newsDate = new.newsDate,
                newsText = new.newsText,
                newsLink = new.newsLink,
                newsImageUrl = new.newsImageUrl,
                newsImageBase64 = new.newsImageBase64
            ))
        }
        return News(
            news = newsList,
            errorMsg = response.errorMsg
        )
    }
}