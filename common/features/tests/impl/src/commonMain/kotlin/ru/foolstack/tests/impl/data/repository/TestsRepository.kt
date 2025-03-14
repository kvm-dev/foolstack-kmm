package ru.foolstack.tests.impl.data.repository

import kotlinx.datetime.Clock
import ru.foolstack.tests.api.model.PassedTestDomain
import ru.foolstack.tests.api.model.PassedTestsDomain
import ru.foolstack.tests.api.model.SendRequestDomain
import ru.foolstack.tests.api.model.SendResultDomain
import ru.foolstack.tests.api.model.TestsDomain
import ru.foolstack.tests.impl.data.repository.local.LocalDataSource
import ru.foolstack.tests.impl.data.repository.network.DelayedTestResultSender
import ru.foolstack.tests.impl.data.repository.network.NetworkDataSource
import ru.foolstack.tests.impl.mapper.Mapper
import ru.foolstack.authorization.impl.data.repository.local.LocalDataSource as AuthorizationLocalDataSource
import ru.foolstack.authorization.impl.data.repository.network.NetworkDataSource as AuthorizationNetworkDataSource

class TestsRepository(private val localDataSource: LocalDataSource,
                      private val networkDataSource: NetworkDataSource,
                      private val authorizationLocalDataSource: AuthorizationLocalDataSource,
                      private val authorizationNetworkDataSource: AuthorizationNetworkDataSource,
                      private val mapper: Mapper,
                      private val delayedTestResultSender: DelayedTestResultSender
) {
    suspend fun getTestsFromServer(): TestsDomain {
        val result = networkDataSource.getTests()
        return if (result.errorMsg.isEmpty()) {
            localDataSource.saveTests(result)
            localDataSource.getTests()
        } else {
            result
        }
    }

    suspend fun getTestsByProfessionFromServer(professionId: Int): TestsDomain {
        val result = networkDataSource.getTestsByProfession(professionId = professionId)
        return if (result.errorMsg.isEmpty()) {
            localDataSource.saveTests(result)
            localDataSource.getTests()
        } else {
            result
        }
    }

    suspend fun getTestsFromLocal(): TestsDomain {
        return localDataSource.getTests()
    }

    suspend fun getPassedTestsFromServer(): PassedTestsDomain{
        var userToken = authorizationLocalDataSource.getTokenFromLocal()
        val refreshToken = authorizationLocalDataSource.getRefreshTokenFromLocal()
        val result = networkDataSource.getPassedTests(userToken = userToken)
        return if(result.errorMsg.isEmpty()){
            result
        } else{
            if(refreshToken.isNotEmpty()){
                val resultAfterRefreshToken = authorizationNetworkDataSource.refreshToken(userToken = userToken, refreshToken = refreshToken)
                if (resultAfterRefreshToken.errorMsg.isEmpty()) {
                    authorizationLocalDataSource.saveUserTokenToLocal(resultAfterRefreshToken.userToken)
                    authorizationLocalDataSource.saveRefreshTokenToLocal(resultAfterRefreshToken.userRefreshToken)
                    userToken = authorizationLocalDataSource.getTokenFromLocal()
                    val newResult = networkDataSource.getPassedTests(userToken = userToken)
                    localDataSource.savePassedTests(newResult)
                    localDataSource.getPassedTests()
                    return newResult
                } else{
                    result
                }
            }
            else{
                result
            }

        }
    }

    suspend fun getPassedTestsFromLocal(): PassedTestsDomain {
        return localDataSource.getPassedTests()
    }

    suspend fun sendTestResultToServer(requestDomain: SendRequestDomain): SendResultDomain {
        var userToken = authorizationLocalDataSource.getTokenFromLocal()
        val refreshToken = authorizationLocalDataSource.getRefreshTokenFromLocal()
        val currentTime = (Clock.System.now().toEpochMilliseconds() + 864000000)/1000//10 days
        val result = networkDataSource.sendTestResult(request = mapper.mapSendResultRequestFromRequestDomain(requestDomain, finishDateTime = currentTime), userToken = userToken)
        return if(result.errorMsg.isEmpty()){
            result
        } else{
            val resultAfterRefreshToken = authorizationNetworkDataSource.refreshToken(userToken = userToken, refreshToken = refreshToken)
            if (resultAfterRefreshToken.errorMsg.isEmpty()) {
                authorizationLocalDataSource.saveUserTokenToLocal(resultAfterRefreshToken.userToken)
                authorizationLocalDataSource.saveRefreshTokenToLocal(resultAfterRefreshToken.userRefreshToken)
                userToken = authorizationLocalDataSource.getTokenFromLocal()
                return networkDataSource.sendTestResult(
                    request = mapper.mapSendResultRequestFromRequestDomain(requestDomain, finishDateTime = currentTime),
                    userToken = userToken
                )
            } else{
                result
            }
        }
    }

    suspend fun sendTestResultToLocal(requestDomain: SendRequestDomain): SendResultDomain {
        val currentTime = (Clock.System.now().toEpochMilliseconds() + 864000000)/1000//10 days
        val passedTest = PassedTestDomain(
            testId = requestDomain.testId,
            testResult = requestDomain.testResult,
            finishTestTime = currentTime
        )
            localDataSource.savePassedTest(passedTest)
            delayedTestResultSender.sendTestResult()
        return SendResultDomain(
            success = true,
            errorMsg = ""
        )

    }
}