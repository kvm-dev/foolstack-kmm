package ru.foolstack.authorization.impl.data.repository.local

import ru.foolstack.storage.prefs.EncryptedPreferences

class LocalDataSource(private val encryptedPreferences: EncryptedPreferences) {

    suspend fun getTokenFromLocal(): String{
        return encryptedPreferences.getToken()
    }

    suspend fun saveUserTokenToLocal(userToken: String){
        encryptedPreferences.saveToken(userToken)
    }

    suspend fun getRefreshTokenFromLocal(): String{
        return encryptedPreferences.getRefreshToken()
    }

    suspend fun saveRefreshTokenToLocal(refreshToken: String){
        encryptedPreferences.saveRefreshToken(refreshToken)
    }
}