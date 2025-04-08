package ru.foolstack.storage.prefs

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import ru.foolstack.utils.PlatformContext

private const val KEYSTORE_ALIAS = "_androidx_security_master_key_"
private const val SECURE_PREFS = "secure_prefs"
private const val BEARER_ALIAS = "Bearer"

private const val TOKEN = "userToken"
private const val REFRESH_TOKEN = "refreshToken"
private const val PROFESSION_ID = "professionId"
private const val THEME = "appTheme"
private const val SYSTEM_THEME = "system"

private const val BOOKS_VERSION = "booksVersion"
private const val EVENTS_VERSION = "eventsVersion"
private const val NEWS_VERSION = "newsVersion"
private const val STUDIES_VERSION = "studiesVersion"



actual class EncryptedPreferences actual constructor(context: PlatformContext) {

    private val spec = KeyGenParameterSpec.Builder(
        KEYSTORE_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setKeySize(256)
        .build()

    private val masterKey: MasterKey = MasterKey.Builder(context.androidContext)
        .setKeyGenParameterSpec(spec)
        .build()

    private val encryptedPreferences = EncryptedSharedPreferences.create(
        context.androidContext,
        SECURE_PREFS,
        masterKey, // masterKey created above
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

    actual fun getToken(): String {
        return encryptedPreferences.getString(TOKEN, "")?:""
    }

    actual fun saveToken(userToken: String) {
        encryptedPreferences.edit().putString(TOKEN, "$BEARER_ALIAS $userToken").apply()
    }

    actual fun saveRefreshToken(refreshToken: String) {
        encryptedPreferences.edit().putString(REFRESH_TOKEN, refreshToken).apply()
    }

    actual fun getRefreshToken(): String {
        return encryptedPreferences.getString(REFRESH_TOKEN, "")?:""
    }

    actual fun getProfessionId(): Int {
        return encryptedPreferences.getInt(PROFESSION_ID, 0)
    }

    actual fun saveProfessionId(professionId: Int) {
        encryptedPreferences.edit().putInt(PROFESSION_ID, professionId).apply()
    }

    actual fun getCurrentAppTheme(): String {
        return encryptedPreferences.getString(THEME, "")?:SYSTEM_THEME
    }

    actual fun setCurrentAppTheme(appTheme: String) {
        encryptedPreferences.edit().putString(THEME, appTheme).apply()
    }

    actual fun clearUserData() {
        encryptedPreferences.edit().clear().apply()
    }

    actual fun getBooksVersion(): Int {
        return encryptedPreferences.getInt(BOOKS_VERSION, 0)
    }

    actual fun updateBooksVersion(version: Int) {
        encryptedPreferences.edit().putInt(BOOKS_VERSION, version).apply()
    }

    actual fun getEventsVersion(): Int {
        return encryptedPreferences.getInt(EVENTS_VERSION, 0)
    }

    actual fun updateEventsVersion(version: Int) {
        encryptedPreferences.edit().putInt(EVENTS_VERSION, version).apply()
    }

    actual fun getNewsVersion(): Int {
        return encryptedPreferences.getInt(NEWS_VERSION, 0)
    }

    actual fun updateNewsVersion(version: Int) {
        encryptedPreferences.edit().putInt(NEWS_VERSION, version).apply()
    }

    actual fun getStudiesVersion(): Int {
        return encryptedPreferences.getInt(STUDIES_VERSION, 0)
    }

    actual fun updateStudiesVersion(version: Int) {
        encryptedPreferences.edit().putInt(STUDIES_VERSION, version).apply()
    }
}