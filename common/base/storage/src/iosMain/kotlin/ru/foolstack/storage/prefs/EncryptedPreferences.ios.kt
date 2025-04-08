package ru.foolstack.storage.prefs

import ru.foolstack.utils.PlatformContext


actual class EncryptedPreferences actual constructor(context: PlatformContext) {
    actual fun getToken(): String {
        TODO("Not yet implemented")
    }

    actual fun saveToken(userToken: String) {
    }

    actual fun saveRefreshToken(refreshToken: String) {
    }

    actual fun getRefreshToken(): String {
        TODO("Not yet implemented")
    }

    actual fun getProfessionId(): Int {
        TODO("Not yet implemented")
    }

    actual fun saveProfessionId(professionId: Int) {
    }

    actual fun getCurrentAppTheme(): String {
        TODO("Not yet implemented")
    }

    actual fun setCurrentAppTheme(appTheme: String) {
    }

    actual fun getBooksVersion(): Int {
        TODO("Not yet implemented")
    }

    actual fun updateBooksVersion(version: Int) {
    }

    actual fun getEventsVersion(): Int {
        TODO("Not yet implemented")
    }

    actual fun updateEventsVersion(version: Int) {
    }

    actual fun getNewsVersion(): Int {
        TODO("Not yet implemented")
    }

    actual fun updateNewsVersion(version: Int) {
    }

    actual fun getStudiesVersion(): Int {
        TODO("Not yet implemented")
    }

    actual fun updateStudiesVersion(version: Int) {
    }

    actual fun clearUserData() {
    }


}