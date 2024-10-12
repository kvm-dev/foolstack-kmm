package ru.foolstack.professions.impl.data.repository.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import ru.foolstack.network.baseUrl
import ru.foolstack.network.exceptionHandler
import ru.foolstack.professions.impl.model.ProfessionsResponse

class ProfessionsApi(private val client: HttpClient) {
    suspend fun getProfessions(): ProfessionsResponse{
        val result = with(client) {
            get("$baseUrl${ProfessionsEndpoints.getProfessions}")
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<ProfessionsResponse>()
        } else{
            ProfessionsResponse(errorMsg = exceptionHandler(result.status))
        }
    }
}