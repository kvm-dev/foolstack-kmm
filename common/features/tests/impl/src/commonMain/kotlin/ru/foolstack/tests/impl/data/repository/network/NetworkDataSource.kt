package ru.foolstack.tests.impl.data.repository.network

import ru.foolstack.tests.api.model.PassedTestsDomain
import ru.foolstack.tests.api.model.TestsDomain
import ru.foolstack.tests.impl.mapper.Mapper
import ru.foolstack.tests.impl.model.TestResultRequest

class NetworkDataSource(private val api: TestsApi, private val mapper: Mapper){

    suspend fun getTests(): TestsDomain  = mapper.mapTestsDomainFromResponse(api.getTests())

    suspend fun getTestsByProfession(professionId: Int): TestsDomain  = mapper.mapTestsDomainFromResponse(api.getTestsByProfession(professionId = professionId))

    suspend fun getPassedTests(userToken: String): PassedTestsDomain = mapper.mapPassedTestsDomainFromResponse(api.getPassedTests(userToken = userToken))

    suspend fun sendTestResult(request: TestResultRequest, userToken: String) = mapper.mapTestResultDomainFromTestResultResponse(api.sendTestResult(request = request, userToken = userToken))
}