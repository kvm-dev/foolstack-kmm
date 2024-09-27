package ru.foolstack.registration.impl.data.repository.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import ru.foolstack.network.baseUrl
import ru.foolstack.network.exceptionHandler
import ru.foolstack.registration.impl.model.request.RegistrationByEmailRequest
import ru.foolstack.registration.impl.model.response.RegistrationByEmailResponse

class RegistrationApi(private val client: HttpClient) {
    suspend fun registrationByEmail(email: String): RegistrationByEmailResponse {
        val result = with(client) {
            post("$baseUrl${RegistrationEndpoints.registrationByEmail}"){
                setBody(RegistrationByEmailRequest(email = email))
            }
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<RegistrationByEmailResponse>()
        } else{
            RegistrationByEmailResponse(errorMsg = exceptionHandler(result.status))
        }
    }

}