package ru.foolstack.tests.impl.mapper

import ru.foolstack.storage.model.PassedTest
import ru.foolstack.storage.model.PassedTests
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
import ru.foolstack.tests.api.model.PassedTestDomain
import ru.foolstack.tests.api.model.PassedTestsDomain
import ru.foolstack.tests.api.model.SendRequestDomain
import ru.foolstack.tests.api.model.SendResultDomain
import ru.foolstack.tests.impl.model.PassedTestsResponse
import ru.foolstack.tests.impl.model.TestResultRequest
import ru.foolstack.tests.impl.model.TestResultResponse
import ru.foolstack.ui.model.TestItem

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

    fun mapPassedTestsDomainFromResponse(response: PassedTestsResponse): PassedTestsDomain {
        val passedTests = ArrayList<PassedTestDomain>()
        response.passedTests.forEach { passedTest->
            passedTests.add(
                PassedTestDomain(
                testId = passedTest.testId,
                testResult = passedTest.testResult,
                finishTestTime = passedTest.finishTestTime
            )
            )
        }
        return PassedTestsDomain(
            success = response.success,
            passedTests = passedTests,
            errorMsg = response.errorMsg
        )
    }

    fun mapPassedTestsDomainFromLocal(response: PassedTests): PassedTestsDomain {
        val passedTests = ArrayList<PassedTestDomain>()
        response.passedTests.forEach { passedTest->
            passedTests.add(
                PassedTestDomain(
                    testId = passedTest.testId,
                    testResult = passedTest.testResult,
                    finishTestTime = passedTest.finishTestTime
                )
            )
        }
        return PassedTestsDomain(
            passedTests = passedTests,
            success = true,
            errorMsg = ""
        )
    }

    fun mapSendResultRequestFromRequestDomain(request: SendRequestDomain, finishDateTime: Long):TestResultRequest{
        return TestResultRequest(testId = request.testId, testResult = request.testResult, finishTestTime = finishDateTime)
    }

    fun mapTestResultDomainFromTestResultResponse(response: TestResultResponse): SendResultDomain{
        return SendResultDomain(
            success = response.success,
            errorMsg = response.errorMsg
        )
    }

    fun mapPassedTestsDomain(response: PassedTests): PassedTestsDomain {
        val passedTests = ArrayList<PassedTestDomain>()
        response.passedTests.forEach { passedTest->
            passedTests.add(
                PassedTestDomain(
                testId = passedTest.testId,
                testResult = passedTest.testResult,
                finishTestTime = passedTest.finishTestTime
            )
            )
        }
        return PassedTestsDomain(
            success = true,
            errorMsg = "",
            passedTests = passedTests
        )
    }

    fun mapPassedTestsDomainToPassedTests(response: PassedTestsDomain): PassedTests {
        val passedTests = ArrayList<PassedTest>()
        response.passedTests.forEach { passedTest->
            passedTests.add(
                PassedTest(
                    testId = passedTest.testId,
                    testResult = passedTest.testResult,
                    finishTestTime = passedTest.finishTestTime
                )
            )
        }
        return PassedTests(
            passedTests = passedTests
        )
    }

    fun mapPassedTestDomainToPassedTest(response: PassedTestDomain): PassedTest {
        return PassedTest(
            testId = response.testId,
            testResult = response.testResult,
            finishTestTime = response.finishTestTime
        )
    }

    fun mapTestsDomainToTestsItems(tests: List<TestDomain>, passedTests: List<PassedTestDomain>):List<TestItem>{
        val testItems = ArrayList<TestItem>()
        tests.forEach { test->
            val lastResult = passedTests.find { it.testId == test.testId }?.testResult?: 0
            val nextTry = passedTests.find { it.testId == test.testId }?.finishTestTime
            testItems.add(TestItem(
                testId = test.testId,
                testName = test.testName,
                lastResult = lastResult,
                questionsSize = test.questions.size,
                timeLimit = test.testTimeLimit,
                difficulty = test.testLevel,
                nextTry = nextTry
            ))
        }
        return testItems
    }

    fun mapVariantsToStringList(variants: List<VariantDomain>?):List<String>{
        val list = ArrayList<String>()
        variants?.forEach {
            list.add(it.variantText)
        }
        return list
    }

}