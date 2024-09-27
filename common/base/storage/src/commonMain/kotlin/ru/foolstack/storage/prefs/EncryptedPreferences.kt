package ru.foolstack.storage.prefs

import ru.foolstack.utils.PlatformContext

expect class EncryptedPreferences(context: PlatformContext) {

    fun getToken(): String
    fun saveToken(userToken: String)
    fun saveRefreshToken(refreshToken: String)
    fun getRefreshToken():String
}