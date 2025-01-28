package ru.foolstack.interview.impl.data.repository.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import ru.foolstack.interview.impl.model.MaterialsResponse
import ru.foolstack.network.baseUrl
import ru.foolstack.network.exceptionHandler

class MaterialsApi(private val client: HttpClient) {
    suspend fun getMaterials(): MaterialsResponse{
        val result = with(client) {
            get("$baseUrl${MaterialEndpoints.getMaterials}")
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<MaterialsResponse>()
        } else{
            MaterialsResponse(errorMsg = exceptionHandler(result.status))
        }
    }

    suspend fun getMaterialsByProfession(professionId: Int): MaterialsResponse{
        val result = with(client) {
            get("$baseUrl${MaterialEndpoints.getMaterialsByProfession}"){
                url {
                    parameters.append("selectedProfession", "$professionId")
                }
            }
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<MaterialsResponse>()
        } else{
            MaterialsResponse(errorMsg = exceptionHandler(result.status))
        }
    }
}