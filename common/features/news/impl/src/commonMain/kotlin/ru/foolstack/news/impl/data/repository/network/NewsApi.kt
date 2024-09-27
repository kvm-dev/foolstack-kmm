package ru.foolstack.news.impl.data.repository.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import ru.foolstack.network.baseUrl
import ru.foolstack.network.exceptionHandler
import ru.foolstack.news.impl.model.NewsResponse

class NewsApi(private val client: HttpClient) {
    suspend fun getNews(): NewsResponse{
        val result = with(client) {
            get("$baseUrl${NewsEndpoints.getNews}")
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<NewsResponse>()
        } else{
            NewsResponse(errorMsg = exceptionHandler(result.status))
        }
    }
}