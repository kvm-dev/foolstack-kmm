package ru.foolstack.settings.api.domain.usecase

import ru.foolstack.settings.api.model.AppThemeDomain

interface SetAppThemeUseCase {
    fun setCurrentAppTheme(appTheme: AppThemeDomain)
}