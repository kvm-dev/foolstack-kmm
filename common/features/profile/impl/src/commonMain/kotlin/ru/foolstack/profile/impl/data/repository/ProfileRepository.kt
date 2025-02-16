package ru.foolstack.profile.impl.data.repository

import ru.foolstack.profile.api.model.DeleteProfileResponseDomain
import ru.foolstack.profile.api.model.ProfileDomain
import ru.foolstack.profile.api.model.UpdateEmailResponseDomain
import ru.foolstack.profile.api.model.UpdateNameResponseDomain
import ru.foolstack.profile.api.model.UpdatePhotoResponseDomain
import ru.foolstack.profile.impl.data.repository.local.LocalDataSource
import ru.foolstack.profile.impl.data.repository.network.NetworkDataSource
import ru.foolstack.authorization.impl.data.repository.local.LocalDataSource as AuthorizationLocalDataSource
import ru.foolstack.authorization.impl.data.repository.network.NetworkDataSource as AuthorizationNetworkDataSource

class ProfileRepository(private val localDataSource: LocalDataSource,
                        private val networkDataSource: NetworkDataSource,
                        private val authorizationLocalDataSource: AuthorizationLocalDataSource,
                        private val authorizationNetworkDataSource: AuthorizationNetworkDataSource) {

    suspend fun getProfileFromServer(): ProfileDomain {
        var userToken = authorizationLocalDataSource.getTokenFromLocal()
        val refreshToken = authorizationLocalDataSource.getRefreshTokenFromLocal()
        val result = networkDataSource.getProfile(userToken)
        return if(result.errorMsg.isEmpty()){
            localDataSource.saveProfile(result)
            localDataSource.getProfile()
        } else{
            val resultAfterRefresh = authorizationNetworkDataSource.refreshToken(userToken = userToken, refreshToken = refreshToken)
            if (resultAfterRefresh.errorMsg.isEmpty()){
                authorizationLocalDataSource.saveUserTokenToLocal(resultAfterRefresh.userToken)
                authorizationLocalDataSource.saveRefreshTokenToLocal(resultAfterRefresh.userRefreshToken)
                userToken = authorizationLocalDataSource.getTokenFromLocal()
                val resultProfile =  networkDataSource.getProfile(userToken)
                if(resultProfile.errorMsg.isEmpty()){
                    localDataSource.saveProfile(resultProfile)
                    return localDataSource.getProfile()
                }
                else return resultProfile
            } else{
                result
            }
        }
    }

    suspend fun getProfileFromLocal():ProfileDomain{
        return localDataSource.getProfile()
    }

    suspend fun updateEmail(email: String): UpdateEmailResponseDomain{
        return networkDataSource.updateEmail(
            userToken = authorizationLocalDataSource.getTokenFromLocal(),
            email = email)
    }

    suspend fun updateName(name: String): UpdateNameResponseDomain{
        return networkDataSource.updateName(
            userToken = authorizationLocalDataSource.getTokenFromLocal(),
            name = name)
    }

    suspend fun updatePhoto(photo: ByteArray): UpdatePhotoResponseDomain{
        return networkDataSource.updatePhoto(
            userToken = authorizationLocalDataSource.getTokenFromLocal(),
            photo = photo)
    }

    suspend fun deleteProfile(): DeleteProfileResponseDomain{
        val result =  networkDataSource.deleteProfile(
            userToken = authorizationLocalDataSource.getTokenFromLocal())
        localDataSource.logout()
        return result
    }

    suspend fun logout(){
        localDataSource.logout()
        localDataSource.getProfile()
    }
}