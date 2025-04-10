package ru.foolstack.splash.impl.data.repository.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import ru.foolstack.network.baseUrl
import ru.foolstack.network.exceptionHandler
import ru.foolstack.splash.impl.model.LastVersionResponse

class SplashApi(private val client: HttpClient) {
    suspend fun checkUpdate(): LastVersionResponse{
        val result = with(client) {
            get("$baseUrl${SplashEndpoints.checkUpdate}")
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<LastVersionResponse>()
        } else{
            LastVersionResponse(errorMsg = exceptionHandler(result.status))
        }
    }
}