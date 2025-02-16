package ru.foolstack.storage.prefs

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import ru.foolstack.utils.PlatformContext

actual class EncryptedPreferences actual constructor(context: PlatformContext) {

    private val spec = KeyGenParameterSpec.Builder(
        "_androidx_security_master_key_",
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
        "secure_prefs",
        masterKey, // masterKey created above
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

    actual fun getToken(): String {
        return encryptedPreferences.getString("userToken", "")?:""
    }

    actual fun saveToken(userToken: String) {
        encryptedPreferences.edit().putString("userToken", "Bearer $userToken").apply()
    }

    actual fun saveRefreshToken(refreshToken: String) {
        encryptedPreferences.edit().putString("refreshToken", refreshToken).apply()
    }

    actual fun getRefreshToken(): String {
        return encryptedPreferences.getString("refreshToken", "")?:""
    }

    actual fun getProfessionId(): Int {
        return encryptedPreferences.getInt("professionId", 0)
    }

    actual fun saveProfessionId(professionId: Int) {
        encryptedPreferences.edit().putInt("professionId", professionId).apply()
    }

    actual fun getCurrentAppTheme(): String {
        return encryptedPreferences.getString("appTheme", "")?:"system"
    }

    actual fun setCurrentAppTheme(appTheme: String) {
        encryptedPreferences.edit().putString("appTheme", appTheme).apply()
    }

    actual fun clearUserData() {
        encryptedPreferences.edit().clear().apply()
    }
}