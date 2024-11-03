package ru.foolstack.authorization.impl.data.repository.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import ru.foolstack.authorization.impl.model.request.AuthByEmailRequest
import ru.foolstack.authorization.impl.model.request.ConfirmAuthAndRegRequest
import ru.foolstack.authorization.impl.model.request.IsUserExistRequest
import ru.foolstack.authorization.impl.model.request.RefreshTokenRequest
import ru.foolstack.authorization.impl.model.response.AuthByEmailResponse
import ru.foolstack.authorization.impl.model.response.AuthByTokenResponse
import ru.foolstack.authorization.impl.model.response.ConfirmAuthAndRegResponse
import ru.foolstack.authorization.impl.model.response.IsUserExistResponse
import ru.foolstack.authorization.impl.model.response.RefreshTokenResponse
import ru.foolstack.network.baseUrl
import ru.foolstack.network.exceptionHandler

class AuthorizationApi(private val client: HttpClient) {
    suspend fun authByEmail(email: String, code: String): AuthByEmailResponse{
        val result = with(client) {
            post("$baseUrl${AuthorizationEndpoints.authorizationByEmail}"){
                setBody(AuthByEmailRequest(email = email, code = code))
            }
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<AuthByEmailResponse>()
        } else{
            AuthByEmailResponse(errorMsg = exceptionHandler(result.status))
        }
    }

    suspend fun authByToken(userToken: String): AuthByTokenResponse{
        val defaultResult: AuthByTokenResponse = try {
            val result = with(client) {
                post("$baseUrl${AuthorizationEndpoints.authByToken}") {
                    header(HttpHeaders.Authorization, userToken)
                }
            }
            if (result.status == HttpStatusCode.OK) {
                result.body<AuthByTokenResponse>()
            } else {
                AuthByTokenResponse(errorMsg = exceptionHandler(result.status))
            }
        } catch (e: Throwable){
            AuthByTokenResponse(errorMsg = e.message?: "Unknown error")
        }
        return defaultResult
    }

    suspend fun confirmAuthAndReg(email: String, code: String): ConfirmAuthAndRegResponse{
        val result = with(client) {
            post("$baseUrl${AuthorizationEndpoints.confirmAuthAndReg}"){
                setBody(ConfirmAuthAndRegRequest(email = email, code = code))
            }
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<ConfirmAuthAndRegResponse>()
        } else{
            ConfirmAuthAndRegResponse(errorMsg = exceptionHandler(result.status))
        }
    }

    suspend fun isUserExist(email: String): IsUserExistResponse{
        val result = with(client) {
            post("$baseUrl${AuthorizationEndpoints.isUserExist}"){
                contentType(ContentType.Application.Json)
                setBody(IsUserExistRequest(email = email))
            }
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<IsUserExistResponse>()
        } else{
            IsUserExistResponse(errorMsg = exceptionHandler(result.status))
        }
    }

    suspend fun refreshToken(userToken: String, refreshToken: String): RefreshTokenResponse{
        val result = with(client) {
            post("$baseUrl${AuthorizationEndpoints.refreshToken}"){
                setBody(RefreshTokenRequest(refreshToken = refreshToken))
                header(HttpHeaders.Authorization, userToken)
            }
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<RefreshTokenResponse>()
        } else{
            RefreshTokenResponse(errorMsg = exceptionHandler(result.status))
        }
    }
}