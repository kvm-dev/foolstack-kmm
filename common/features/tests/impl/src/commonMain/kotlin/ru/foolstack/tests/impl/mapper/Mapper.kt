package ru.foolstack.tests.impl.mapper

import ru.foolstack.storage.model.Question
import ru.foolstack.storage.model.Test
import ru.foolstack.storage.model.Tests
import ru.foolstack.storage.model.Variant
import ru.foolstack.tests.api.model.QuestionDomain
import ru.foolstack.tests.api.model.TestDomain
import ru.foolstack.tests.api.model.TestProfessionDomain
import ru.foolstack.tests.api.model.TestsDomain
import ru.foolstack.tests.api.model.VariantDomain
import ru.foolstack.tests.impl.model.QuestionResponse
import ru.foolstack.tests.impl.model.TestResponse
import ru.foolstack.tests.impl.model.TestsResponse
import ru.foolstack.tests.impl.model.VariantResponse
import ru.foolstack.storage.model.ProfessionListItem

class Mapper {
    fun mapTestsDomainFromResponse(response: TestsResponse): TestsDomain {
        return TestsDomain(
            tests = mapTestDomainFromResponse(response.tests),
            errorMsg = response.errorMsg
        )
    }

    private fun mapTestDomainFromResponse(response: List<TestResponse>): List<TestDomain> {
        val testData = ArrayList<TestDomain>()
        response.forEach { test ->
            val professions = ArrayList<TestProfessionDomain>()
            test.professions.forEach { profession->
                professions.add(TestProfessionDomain(
                    professionId = profession.professionId,
                    professionName = profession.professionName
                ))
            }
            testData.add(
                TestDomain(
                    testId = test.testId,
                    testName = test.testName,
                    testLevel = test.testLevel,
                    testTimeLimit = test.testTimeLimit,
                    questions = mapQuestionDomainFromResponse(test.questions),
                    professions = professions
                )
            )
        }
        return testData
    }

    private fun mapQuestionDomainFromResponse(response: List<QuestionResponse>): List<QuestionDomain> {
        val questionsData = ArrayList<QuestionDomain>()
        response.forEach { question ->
            questionsData.add(
                QuestionDomain(
                    questionId = question.questionId,
                    questionText = question.questionText,
                    variants = mapVariantDomainFromResponse(question.variants)
                )
            )
        }
        return questionsData

    }

    private fun mapVariantDomainFromResponse(response: List<VariantResponse>): List<VariantDomain> {
        val variantsData = ArrayList<VariantDomain>()
        response.forEach { variant ->
            variantsData.add(
                VariantDomain(
                    variantId = variant.variantId,
                    variantText = variant.variantText,
                    isRight = variant.isRight
                )
            )
        }
        return variantsData
    }

    fun mapTestsDomain(response: Tests): TestsDomain {
        return TestsDomain(
            tests = mapTestDomain(response.tests),
            errorMsg = ""
        )
    }

    private fun mapTestDomain(response: List<Test>): List<TestDomain> {
        val testData = ArrayList<TestDomain>()
        response.forEach { test ->
            val professions = ArrayList<TestProfessionDomain>()
            test.professions.forEach { profession->
                professions.add(TestProfessionDomain(
                    professionId = profession.professionId,
                    professionName = profession.professionName
                ))
            }
            testData.add(
                TestDomain(
                    testId = test.testId,
                    testName = test.testName,
                    testLevel = test.testLevel,
                    testTimeLimit = test.testTimeLimit,
                    questions = mapQuestionDomain(test.questions),
                    professions = professions
                )
            )
        }
        return testData
    }

    private fun mapQuestionDomain(response: List<Question>): List<QuestionDomain>{
        val questionsData = ArrayList<QuestionDomain>()
        response.forEach { question->
            questionsData.add(
                QuestionDomain(
                questionId = question.questionId,
                    questionText = question.questionText,
                    variants = mapVariantDomain(question.variants)
                )
            )
        }
        return questionsData
    }

    private fun mapVariantDomain(response: List<Variant>): List<VariantDomain>{
        val variantsData = ArrayList<VariantDomain>()
        response.forEach { variant->
            variantsData.add(
                VariantDomain(
                variantId = variant.variantId,
                variantText = variant.variantText,
                isRight = variant.isRight
                )
            )
        }
        return variantsData
    }

    fun map(response: TestsDomain):Tests{
        return Tests(
            tests = mapTestFromDomain(response.tests)
        )
    }

    private fun mapTestFromDomain(response: List<TestDomain>): List<Test>{
        val testData = ArrayList<Test>()
        response.forEach { test->
            val professions = ArrayList<ProfessionListItem>()
            test.professions.forEach { profession->
                professions.add(ProfessionListItem(
                    professionId = profession.professionId,
                    professionName = profession.professionName
                ))
            }
            testData.add(Test(
                testId =  test.testId,
                testName = test.testName,
                testLevel = test.testLevel,
                testTimeLimit = test.testTimeLimit,
                questions = mapQuestionFromDomain(test.questions),
                professions = professions
                )
            )
        }
        return testData
    }

    private fun mapQuestionFromDomain(response: List<QuestionDomain>): List<Question>{
        val questionsData = ArrayList<Question>()
        response.forEach { question->
            questionsData.add(Question(
                questionId = question.questionId,
                questionText = question.questionText,
                variants = mapVariantFromDomain(question.variants)
                )
            )
        }
        return questionsData
    }

    private fun mapVariantFromDomain(response: List<VariantDomain>): List<Variant>{
        val variantsData = ArrayList<Variant>()
        response.forEach { variant->
            variantsData.add(Variant(
                variantId = variant.variantId,
                variantText = variant.variantText,
                isRight = variant.isRight
                )
            )
        }
        return variantsData
    }

}