package ru.foolstack.tests.impl.data.repository.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import ru.foolstack.network.baseUrl
import ru.foolstack.network.exceptionHandler
import ru.foolstack.tests.impl.model.TestsResponse

class TestsApi(private val client: HttpClient) {
    suspend fun getTests(): TestsResponse{
        val result = with(client) {
            get("$baseUrl${TestsEndpoints.getTests}")
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<TestsResponse>()
        } else{
            TestsResponse(errorMsg = exceptionHandler(result.status))
        }
    }
}