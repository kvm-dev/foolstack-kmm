package ru.foolstack.settings.impl.mapper

import ru.foolstack.settings.api.model.AppThemeDomain
import ru.foolstack.settings.impl.model.AppTheme

class Mapper {
    fun mapAppThemeToDomain(theme: AppTheme): AppThemeDomain {
        return when(theme){
            AppTheme.SYSTEM_THEME -> AppThemeDomain.SYSTEM_THEME
            AppTheme.LIGHT_THEME -> AppThemeDomain.LIGHT_THEME
            AppTheme.DARK_THEME -> AppThemeDomain.DARK_THEME
        }
    }

    fun mapAppThemeFromDomain(theme: AppThemeDomain): AppTheme {
        return when(theme){
            AppThemeDomain.SYSTEM_THEME -> AppTheme.SYSTEM_THEME
            AppThemeDomain.LIGHT_THEME -> AppTheme.LIGHT_THEME
            AppThemeDomain.DARK_THEME -> AppTheme.DARK_THEME
        }
    }

}