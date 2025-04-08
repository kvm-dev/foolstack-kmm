package ru.foolstack.books.impl.data.repository.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import ru.foolstack.books.impl.model.BooksResponse
import ru.foolstack.books.impl.model.BooksVersionResponse
import ru.foolstack.network.baseUrl
import ru.foolstack.network.exceptionHandler

class BooksApi(private val client: HttpClient) {
    suspend fun getBooks(): BooksResponse{
        val result = with(client) {
            get("$baseUrl${BooksEndpoints.getBooks}")
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<BooksResponse>()
        } else{
            BooksResponse(errorMsg = exceptionHandler(result.status))
        }
    }

    suspend fun getVersion(): BooksVersionResponse{
        val result = with(client) {
            get("$baseUrl${BooksEndpoints.getBooksVersion}")
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<BooksVersionResponse>()
        } else{
            BooksVersionResponse(errorMsg = exceptionHandler(result.status))
        }
    }
}