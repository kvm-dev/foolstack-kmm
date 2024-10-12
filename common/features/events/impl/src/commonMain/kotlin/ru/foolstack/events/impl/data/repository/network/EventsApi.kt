package ru.foolstack.events.impl.data.repository.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import ru.foolstack.events.impl.model.EventsResponse
import ru.foolstack.network.baseUrl
import ru.foolstack.network.exceptionHandler

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