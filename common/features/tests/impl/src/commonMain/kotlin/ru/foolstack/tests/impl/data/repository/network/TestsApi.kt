package ru.foolstack.tests.impl.data.repository.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import ru.foolstack.network.baseUrl
import ru.foolstack.network.exceptionHandler
import ru.foolstack.tests.impl.model.PassedTestsResponse
import ru.foolstack.tests.impl.model.TestResultRequest
import ru.foolstack.tests.impl.model.TestResultResponse
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

    suspend fun getTestsByProfession(professionId: Int): TestsResponse{
        val result = with(client) {
            get("$baseUrl${TestsEndpoints.getTestsByProfession}"){
                url {
                    parameters.append("selectedProfession", "$professionId")
                }
            }
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<TestsResponse>()
        } else{
            TestsResponse(errorMsg = exceptionHandler(result.status))
        }
    }

    suspend fun getPassedTests(userToken: String): PassedTestsResponse {
        val result = with(client) {
            get("$baseUrl${TestsEndpoints.getPassedTests}"){
                header(HttpHeaders.Authorization, userToken)
            }
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<PassedTestsResponse>()
        } else{
            PassedTestsResponse(errorMsg = exceptionHandler(result.status))
        }
    }

    suspend fun sendTestResult(request: TestResultRequest, userToken: String): TestResultResponse {
        val result = with(client) {
            post("$baseUrl${TestsEndpoints.sendTestResult}"){
                header(HttpHeaders.Authorization, userToken)
                setBody(request)
            }
        }
        return if(result.status == HttpStatusCode.OK) {
            result.body<TestResultResponse>()
        } else{
            TestResultResponse(errorMsg = exceptionHandler(result.status))
        }
    }
}