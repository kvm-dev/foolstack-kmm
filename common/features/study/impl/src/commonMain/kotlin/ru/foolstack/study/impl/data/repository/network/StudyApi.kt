package ru.foolstack.study.impl.data.repository.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import ru.foolstack.network.baseUrl
import ru.foolstack.network.exceptionHandler
import ru.foolstack.study.impl.model.StudiesResponse

class StudyApi(private val client: HttpClient) {
    suspend fun getStudies(): StudiesResponse{
        val result = with(client) {
            get("$baseUrl${StudyEndpoints.getStudies}")
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<StudiesResponse>()
        } else{
            StudiesResponse(errorMsg = exceptionHandler(result.status))
        }
    }
}