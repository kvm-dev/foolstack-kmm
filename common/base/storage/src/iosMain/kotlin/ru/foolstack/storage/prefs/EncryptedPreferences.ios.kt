package ru.foolstack.storage.prefs

import ru.foolstack.utils.PlatformContext

actual class EncryptedPreferences actual constructor(context: PlatformContext) {

    actual fun getToken(): String {
       return ""
    }

    actual fun saveToken(userToken: String) {
    }

    actual fun saveRefreshToken(refreshToken: String) {
    }

    actual fun getRefreshToken(): String {
        return ""
    }
}