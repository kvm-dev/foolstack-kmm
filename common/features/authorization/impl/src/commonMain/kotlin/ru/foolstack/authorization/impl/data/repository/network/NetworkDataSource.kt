package ru.foolstack.authorization.impl.data.repository.network

import ru.foolstack.authorization.api.model.AuthByEmailDomain
import ru.foolstack.authorization.api.model.AuthByTokenDomain
import ru.foolstack.authorization.api.model.ConfirmAuthAndRegDomain
import ru.foolstack.authorization.api.model.IsUserExistDomain
import ru.foolstack.authorization.impl.mapper.Mapper
import ru.foolstack.authorization.impl.model.response.RefreshTokenResponse

class NetworkDataSource(private val api: AuthorizationApi, private val mapper: Mapper){

    suspend fun authByEmail(email: String, code: String): AuthByEmailDomain {
        return mapper.map(api.authByEmail(email = email, code = code))
    }

    suspend fun authByToken(userToken: String):AuthByTokenDomain{
        return mapper.map(api.authByToken(userToken))
    }

    suspend fun confirmAuthAndReg(email:String, code: String):ConfirmAuthAndRegDomain{
        return mapper.map(api.confirmAuthAndReg(email = email, code = code))
    }

    suspend fun isUserExist(email: String):IsUserExistDomain{
        return mapper.map(api.isUserExist(email))
    }

    suspend fun refreshToken(userToken: String, refreshToken: String):RefreshTokenResponse{
        return api.refreshToken(userToken = userToken, refreshToken = refreshToken)
    }
}