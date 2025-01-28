package ru.foolstack.tests.impl.domain.interactor

import ru.foolstack.events.api.domain.usecase.GetEventsUseCase
import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.network.di.networkModule
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.professions.api.domain.usecase.GetProfessionsUseCase
import ru.foolstack.tests.api.domain.usecase.GetPassedTestsUseCase
import ru.foolstack.tests.api.domain.usecase.GetTestsUseCase
import ru.foolstack.tests.api.domain.usecase.SendTestResultUseCase
import ru.foolstack.tests.api.model.SendRequestDomain
import ru.foolstack.tests.api.model.SendResultDomain

class TestCardInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val sendTestResultUseCase: SendTestResultUseCase,
    private val getTestsUseCase: GetTestsUseCase,
    private val getPassedTestsUseCase: GetPassedTestsUseCase,
    private val getProfessionsUseCase: GetProfessionsUseCase
){
    val testState = getTestsUseCase.testsState

    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()

    private suspend fun sendTestResultToServer(testId: Int, testResult: Int):SendResultDomain{
        val request = SendRequestDomain(
            testId = testId,
            testResult = testResult
        )
        val result = sendTestResultUseCase.sendTestResult(request)
        getTestsUseCase.getTestsByProfession(professionId = getProfessionsUseCase.getProfessionId())
        getPassedTestsUseCase.getPassedTests()
        return result
    }

    private suspend fun sendTestResultToLocal(testId: Int, testResult: Int):SendResultDomain{
        val request = SendRequestDomain(
            testId = testId,
            testResult = testResult
        )
        val result = sendTestResultUseCase.sendTestResult(request, toLocal = true)
        getTestsUseCase.getTestsByProfession(professionId = getProfessionsUseCase.getProfessionId(), fromLocal = true)
        getPassedTestsUseCase.getPassedTests(true)
        return result
    }

    suspend fun saveTestResult(testId: Int, testResult: Int):String{
        val result =
        if(getNetworkStateUseCase.isNetworkAvailable()){
            sendTestResultToServer(testId = testId, testResult = testResult)
        }
        else{
            sendTestResultToLocal(testId = testId, testResult = testResult)
        }
        return result.errorMsg.ifEmpty { "" }
    }
}