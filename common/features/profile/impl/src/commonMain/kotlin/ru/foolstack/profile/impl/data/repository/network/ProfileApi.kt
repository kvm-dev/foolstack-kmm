package ru.foolstack.profile.impl.data.repository.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import ru.foolstack.network.baseUrl
import ru.foolstack.network.exceptionHandler
import ru.foolstack.profile.impl.model.ProfileResponse
import ru.foolstack.profile.impl.model.request.UpdateEmailRequest
import ru.foolstack.profile.impl.model.request.UpdateNameRequest
import ru.foolstack.profile.impl.model.response.DeleteProfileResponse
import ru.foolstack.profile.impl.model.response.UpdateEmailResponse
import ru.foolstack.profile.impl.model.response.UpdateNameResponse
import ru.foolstack.profile.impl.model.response.UpdatePhotoResponse

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

    suspend fun updateEmail(userToken: String, email: String): UpdateEmailResponse {
        val result = with(client) {
            post("$baseUrl${ProfileEndpoints.updateEmail}"){
                header(HttpHeaders.Authorization, userToken)
                setBody(UpdateEmailRequest(email = email))
            }
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<UpdateEmailResponse>()
        } else{
            UpdateEmailResponse(errorMsg = exceptionHandler(result.status))
        }
    }

    suspend fun updateName(userToken: String, name: String): UpdateNameResponse {
        val result = with(client) {
            post("$baseUrl${ProfileEndpoints.updateName}"){
                header(HttpHeaders.Authorization, userToken)
                setBody(UpdateNameRequest(name = name))
            }
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<UpdateNameResponse>()
        } else{
            UpdateNameResponse(errorMsg = exceptionHandler(result.status))
        }
    }

    suspend fun updatePhoto(userToken: String, photo: ByteArray
    ): UpdatePhotoResponse {
        val result = with(client) {
            post("$baseUrl${ProfileEndpoints.updatePhoto}"){
                header(HttpHeaders.Authorization, userToken)
                setBody(
                    MultiPartFormDataContent(
                        formData{
                            append("profileFile", photo, Headers.build {
                                append(HttpHeaders.ContentType, "image/*")
                                append(HttpHeaders.ContentDisposition, "filename=profileFile.png")
                            })
                        }
                )
                )
            }
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<UpdatePhotoResponse>()
        } else{
            UpdatePhotoResponse(errorMsg = exceptionHandler(result.status))
        }
    }

    suspend fun deleteProfile(userToken: String): DeleteProfileResponse {
        val result = with(client) {
            post("$baseUrl${ProfileEndpoints.deleteProfile}"){
                header(HttpHeaders.Authorization, userToken)
            }
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<DeleteProfileResponse>()
        } else{
            DeleteProfileResponse(errorMsg = exceptionHandler(result.status))
        }
    }
}