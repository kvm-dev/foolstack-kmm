package ru.foolstack.profile.impl.data.repository.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import ru.foolstack.network.baseUrl
import ru.foolstack.network.exceptionHandler
import ru.foolstack.profile.impl.model.ProfileResponse

class ProfileApi(private val client: HttpClient) {
    suspend fun getProfile(userToken: String
    ): ProfileResponse{
        val result = with(client) {
            get("$baseUrl${ProfileEndpoints.getProfile}"){
                header(HttpHeaders.Authorization, userToken)
            }
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<ProfileResponse>()
        } else{
            ProfileResponse(errorMsg = exceptionHandler(result.status))
        }
    }
}