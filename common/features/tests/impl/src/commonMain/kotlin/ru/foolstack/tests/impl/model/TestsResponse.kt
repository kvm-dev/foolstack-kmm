package ru.foolstack.tests.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TestsResponse(
    @SerialName("tests") val tests: List<TestResponse> = listOf(),
    @SerialName("errorMsg") val errorMsg: String
)

@Serializable
data class TestResponse(
    @SerialName("testId") val testId: Int = 0,
    @SerialName("testName") val testName: String = "",
    @SerialName("testLevel") val testLevel: Int = 0,
    @SerialName("testTimeLimit") val testTimeLimit: Int = 0,
    @SerialName("questions") val questions: List<QuestionResponse> = listOf(),
    @SerialName("professions") val professions: List<Int> = listOf()
)

@Serializable
data class QuestionResponse(
    @SerialName("questionId") val questionId: Int = 0,
    @SerialName("questionText") val questionText: String = "",
    @SerialName("variants") val variants: List<VariantResponse> = listOf()
)

@Serializable
data class VariantResponse(
    @SerialName("variantId") val variantId: Int = 0,
    @SerialName("variantText") val variantText: String = "",
    @SerialName("isRight") val isRight: Boolean = false
)