package ru.foolstack.authorization.impl.mapper

import ru.foolstack.authorization.api.model.AuthByEmailDomain
import ru.foolstack.authorization.api.model.AuthByTokenDomain
import ru.foolstack.authorization.api.model.ConfirmAuthAndRegDomain
import ru.foolstack.authorization.api.model.IsUserExistDomain
import ru.foolstack.authorization.impl.model.response.AuthByEmailResponse
import ru.foolstack.authorization.impl.model.response.AuthByTokenOfflineResponse
import ru.foolstack.authorization.impl.model.response.AuthByTokenResponse
import ru.foolstack.authorization.impl.model.response.ConfirmAuthAndRegResponse
import ru.foolstack.authorization.impl.model.response.IsUserExistResponse

class Mapper {

    fun map(response: AuthByEmailResponse): AuthByEmailDomain{
        return AuthByEmailDomain(
            userToken = response.userToken,
            userRefreshToken = response.userRefreshToken,
            errorMsg = response.errorMsg)
    }

    fun map(response: AuthByTokenResponse): AuthByTokenDomain{
        return AuthByTokenDomain(
            success = response.success,
            errorMsg = response.errorMsg
        )
    }

    fun map(response: ConfirmAuthAndRegResponse): ConfirmAuthAndRegDomain{
        return ConfirmAuthAndRegDomain(
            success = response.success,
            userToken = response.userToken,
            userRefreshToken = response.userRefreshToken,
            errorMsg = response.errorMsg
        )
    }

    fun map(response: IsUserExistResponse): IsUserExistDomain{
        return IsUserExistDomain(
            success = response.success,
            errorMsg = response.errorMsg
        )
    }
}