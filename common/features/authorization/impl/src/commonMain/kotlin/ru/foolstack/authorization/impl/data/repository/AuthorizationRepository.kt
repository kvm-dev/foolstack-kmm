package ru.foolstack.authorization.impl.data.repository

import NetworkError
import ru.foolstack.authorization.api.model.AuthByEmailDomain
import ru.foolstack.authorization.api.model.AuthByTokenDomain
import ru.foolstack.authorization.api.model.ConfirmAuthAndRegDomain
import ru.foolstack.authorization.api.model.IsUserExistDomain
import ru.foolstack.authorization.impl.data.repository.local.LocalDataSource
import ru.foolstack.authorization.impl.data.repository.network.DelayedAuthByTokenLogger
import ru.foolstack.authorization.impl.data.repository.network.NetworkDataSource

class AuthorizationRepository(private val localDataSource: LocalDataSource,
                              private val networkDataSource: NetworkDataSource,
                              private val delayedAuthByTokenLogger: DelayedAuthByTokenLogger) {

    suspend fun authByEmail(email: String, code:String):AuthByEmailDomain{
        val result = networkDataSource.authByEmail(email = email, code = code)
        return if(result.errorMsg.isEmpty()){
            localDataSource.saveUserTokenToLocal(userToken = result.userToken)
            localDataSource.saveRefreshTokenToLocal(refreshToken = result.userRefreshToken)
            AuthByEmailDomain(userToken = localDataSource.getTokenFromLocal(),
                userRefreshToken = localDataSource.getRefreshTokenFromLocal(),
                errorMsg = "")
        }
        else{
            return result
        }
    }

    suspend fun authByToken():AuthByTokenDomain{
        val userToken = localDataSource.getTokenFromLocal()
        val refreshToken = localDataSource.getTokenFromLocal()
        val result = networkDataSource.authByToken(userToken)
        if(result.errorMsg.isEmpty()){
            return result
        }
        else{
            if(userToken.isNotEmpty() && result.errorMsg==NetworkError.TokenExpired().message){
                val resultAfterRefresh = networkDataSource.refreshToken(userToken = userToken, refreshToken = refreshToken)
                return if (resultAfterRefresh.errorMsg.isEmpty()){
                    localDataSource.saveUserTokenToLocal(resultAfterRefresh.userToken)
                    localDataSource.saveRefreshTokenToLocal(resultAfterRefresh.userRefreshToken)
                    AuthByTokenDomain(
                        success = resultAfterRefresh.success,
                        errorMsg = resultAfterRefresh.errorMsg
                    )
                } else{
                    AuthByTokenDomain(
                        success = resultAfterRefresh.success,
                        errorMsg = resultAfterRefresh.errorMsg
                    )
                }
            }
            else{
                return AuthByTokenDomain(
                    success = result.success,
                    errorMsg = result.errorMsg
                )
            }
        }
    }

    suspend fun confirmAuthAndReg(email: String, code: String): ConfirmAuthAndRegDomain{
        val result = networkDataSource.confirmAuthAndReg(email = email, code = code)
        return if(result.errorMsg.isEmpty()){
            localDataSource.saveUserTokenToLocal(result.userToken)
            localDataSource.saveRefreshTokenToLocal(result.userRefreshToken)
            ConfirmAuthAndRegDomain(
                success = result.success,
                userToken = result.userToken,
                userRefreshToken = result.userRefreshToken,
                errorMsg = result.errorMsg
            )
        } else result
    }

    suspend fun isUserExist(email: String): IsUserExistDomain{
        return networkDataSource.isUserExist(email = email)
    }

    suspend fun getTokenFromLocal():String{
        return localDataSource.getTokenFromLocal()
    }

    fun authByTokenOfflineLog(){
        delayedAuthByTokenLogger.sendLog()
    }
}