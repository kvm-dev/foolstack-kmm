package ru.foolstack.tests.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PassedTestsResponse(
    @SerialName("success") val success: Boolean = false,
    @SerialName("passedTests") val passedTests: List<PassedTestResponse> = listOf(),
    @SerialName("errorMsg") val errorMsg: String = ""
)

@Serializable
data class PassedTestResponse(
    @SerialName("testId") val testId: Int = 0,
    @SerialName("testResult") val testResult: Int = 0,
    @SerialName("finishTestTime") val finishTestTime: Long = 0
)