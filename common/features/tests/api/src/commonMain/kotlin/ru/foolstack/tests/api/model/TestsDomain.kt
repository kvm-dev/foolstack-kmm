package ru.foolstack.tests.api.model

data class TestsDomain(
    val tests: List<TestDomain>,
    val errorMsg: String
)

data class TestDomain(
    val testId: Int,
    val testName: String,
    val testLevel: Int,
    val testTimeLimit: Int,
    val questions: List<QuestionDomain>,
    val professions: List<TestProfessionDomain>
)

data class QuestionDomain(
    val questionId: Int,
    val questionText: String,
    val variants: List<VariantDomain>
)

data class VariantDomain(
    val variantId: Int,
    val variantText: String,
    val isRight: Boolean
)

data class TestProfessionDomain(
    val professionId: Int,
    val professionName: String
)