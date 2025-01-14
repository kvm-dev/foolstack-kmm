package ru.foolstack.tests.impl.data.repository

import ru.foolstack.tests.api.model.PassedTestsDomain
import ru.foolstack.tests.api.model.SendRequestDomain
import ru.foolstack.tests.api.model.SendResultDomain
import ru.foolstack.tests.api.model.TestsDomain
import ru.foolstack.tests.impl.data.repository.local.LocalDataSource
import ru.foolstack.tests.impl.data.repository.network.NetworkDataSource
import ru.foolstack.tests.impl.mapper.Mapper
import ru.foolstack.authorization.impl.data.repository.local.LocalDataSource as AuthorizationLocalDataSource
import ru.foolstack.authorization.impl.data.repository.network.NetworkDataSource as AuthorizationNetworkDataSource

class TestsRepository(private val localDataSource: LocalDataSource,
                      private val networkDataSource: NetworkDataSource,
                      private val authorizationLocalDataSource: AuthorizationLocalDataSource,
                      private val authorizationNetworkDataSource: AuthorizationNetworkDataSource,
                      private val mapper: Mapper
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
    }

    suspend fun getPassedTestsFromLocal(): PassedTestsDomain {
        return localDataSource.getPassedTests()
    }

    suspend fun sendTestResult(requestDomain: SendRequestDomain): SendResultDomain {
        var userToken = authorizationLocalDataSource.getTokenFromLocal()
        val refreshToken = authorizationLocalDataSource.getRefreshTokenFromLocal()
        val result = networkDataSource.sendTestResult(request = mapper.mapSendResultRequestFromRequestDomain(requestDomain), userToken = userToken)
        return if(result.errorMsg.isEmpty()){
            result
        } else{
            val resultAfterRefreshToken = authorizationNetworkDataSource.refreshToken(userToken = userToken, refreshToken = refreshToken)
            if (resultAfterRefreshToken.errorMsg.isEmpty()) {
                authorizationLocalDataSource.saveUserTokenToLocal(resultAfterRefreshToken.userToken)
                authorizationLocalDataSource.saveRefreshTokenToLocal(resultAfterRefreshToken.userRefreshToken)
                userToken = authorizationLocalDataSource.getTokenFromLocal()
                return networkDataSource.sendTestResult(
                    request = mapper.mapSendResultRequestFromRequestDomain(requestDomain),
                    userToken = userToken
                )
            } else{
                result
            }
        }
    }
}