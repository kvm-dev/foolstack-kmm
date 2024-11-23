package ru.foolstack.comments.impl.data.repository

import ru.foolstack.comments.api.model.MaterialCommentRequestDomain
import ru.foolstack.comments.api.model.MaterialCommentResponseDomain
import ru.foolstack.comments.impl.data.repository.network.NetworkDataSource
import ru.foolstack.authorization.impl.data.repository.local.LocalDataSource as AuthorizationLocalDataSource
import ru.foolstack.authorization.impl.data.repository.network.NetworkDataSource as AuthorizationNetworkDataSource

class CommentsRepository(private val networkDataSource: NetworkDataSource,
                         private val authorizationLocalDataSource: AuthorizationLocalDataSource,
                         private val authorizationNetworkDataSource: AuthorizationNetworkDataSource) {

    suspend fun sendMaterialComment(requestDomain: MaterialCommentRequestDomain): MaterialCommentResponseDomain {
        var userToken = authorizationLocalDataSource.getTokenFromLocal()
        val refreshToken = authorizationLocalDataSource.getRefreshTokenFromLocal()
        val result = networkDataSource.sendMaterialComment(request = requestDomain, userToken = userToken)
        return if(result.errorMsg.isEmpty()){
            result
        } else{
            val resultAfterRefreshToken = authorizationNetworkDataSource.refreshToken(userToken = userToken, refreshToken = refreshToken)
            if (resultAfterRefreshToken.errorMsg.isEmpty()) {
                authorizationLocalDataSource.saveUserTokenToLocal(resultAfterRefreshToken.userToken)
                authorizationLocalDataSource.saveRefreshTokenToLocal(resultAfterRefreshToken.userRefreshToken)
                userToken = authorizationLocalDataSource.getTokenFromLocal()
                return networkDataSource.sendMaterialComment(
                    request = requestDomain,
                    userToken = userToken
                )
            } else{
                result
            }
        }
    }
}