package ru.foolstack.settings.impl.data.repository.local

import ru.foolstack.settings.impl.model.AppTheme
import ru.foolstack.storage.prefs.EncryptedPreferences

class LocalDataSource(private val encryptedPreferences: EncryptedPreferences) {
     fun getAppTheme():AppTheme {
         return when(encryptedPreferences.getCurrentAppTheme()){
            "system" -> AppTheme.SYSTEM_THEME
             "light" -> AppTheme.LIGHT_THEME
             "dark" -> AppTheme.DARK_THEME
             else -> {AppTheme.SYSTEM_THEME}
         }
    }
     fun setAppTheme(theme: AppTheme){
        when(theme){
            AppTheme.SYSTEM_THEME-> encryptedPreferences.setCurrentAppTheme("system")
            AppTheme.LIGHT_THEME-> encryptedPreferences.setCurrentAppTheme("light")
            AppTheme.DARK_THEME -> encryptedPreferences.setCurrentAppTheme("dark")
        }
    }
}