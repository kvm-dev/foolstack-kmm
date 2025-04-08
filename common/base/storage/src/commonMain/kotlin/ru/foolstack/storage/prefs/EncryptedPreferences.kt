package ru.foolstack.storage.prefs

import io.ktor.http.content.Version
import ru.foolstack.utils.PlatformContext

expect class EncryptedPreferences(context: PlatformContext) {

    fun getToken(): String
    fun saveToken(userToken: String)
    fun saveRefreshToken(refreshToken: String)
    fun getRefreshToken():String
    fun getProfessionId(): Int
    fun saveProfessionId(professionId:Int)
    fun getCurrentAppTheme():String
    fun setCurrentAppTheme(appTheme: String)
    fun getBooksVersion():Int
    fun updateBooksVersion(version: Int)
    fun getEventsVersion():Int
    fun updateEventsVersion(version: Int)
    fun getNewsVersion(): Int
    fun updateNewsVersion(version: Int)
    fun getStudiesVersion(): Int
    fun updateStudiesVersion(version: Int)


    fun clearUserData()
}