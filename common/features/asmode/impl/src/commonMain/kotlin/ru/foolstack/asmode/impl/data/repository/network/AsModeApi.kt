package ru.foolstack.asmode.impl.data.repository.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import ru.foolstack.asmode.impl.model.AsModeResponse
import ru.foolstack.network.baseUrl
import ru.foolstack.network.exceptionHandler

class AsModeApi(private val client: HttpClient) {
    suspend fun getAsMode(): AsModeResponse{
        val result = with(client) {
            get("$baseUrl${AsModeEndpoints.getAsMode}")
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<AsModeResponse>()
        } else{
            AsModeResponse(errorMsg = exceptionHandler(result.status))
        }
    }
}