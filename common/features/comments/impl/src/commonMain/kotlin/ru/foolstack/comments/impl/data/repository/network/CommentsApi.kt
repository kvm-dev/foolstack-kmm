package ru.foolstack.comments.impl.data.repository.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import ru.foolstack.comments.impl.model.MaterialCommentRequest
import ru.foolstack.comments.impl.model.MaterialCommentResponse
import ru.foolstack.network.baseUrl
import ru.foolstack.network.exceptionHandler

class CommentsApi(private val client: HttpClient) {
    suspend fun sendMaterialComment(request: MaterialCommentRequest, userToken: String): MaterialCommentResponse {
        val result = with(client) {
            post("$baseUrl${CommentsEndpoints.commentMaterial}"){
                header(HttpHeaders.Authorization, userToken)
                setBody(request)
            }
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<MaterialCommentResponse>()
        } else{
            MaterialCommentResponse(errorMsg = exceptionHandler(result.status))
        }
    }

}