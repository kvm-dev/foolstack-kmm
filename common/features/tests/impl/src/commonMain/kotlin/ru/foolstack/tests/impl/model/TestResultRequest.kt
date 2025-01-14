package ru.foolstack.tests.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TestResultRequest(
    @SerialName("testId") val testId: Int,
    @SerialName("testResult") val testResult: Int
)