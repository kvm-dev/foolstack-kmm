package ru.foolstack.events.impl.data.repository.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import io.ktor.util.toByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.foolstack.events.impl.model.EventsResponse
import ru.foolstack.network.baseUrl
import ru.foolstack.network.exceptionHandler
import ru.foolstack.network.ext.toBase64

class EventsApi(private val client: HttpClient) {
    suspend fun getEvents(): EventsResponse {
        val result = with(client) {
            get("$baseUrl${EventsEndpoints.getEvents}")
        }
        return if (result.status == HttpStatusCode.OK) {
            result.body<EventsResponse>()
        } else {
            EventsResponse(errorMsg = exceptionHandler(result.status))
        }
    }
}