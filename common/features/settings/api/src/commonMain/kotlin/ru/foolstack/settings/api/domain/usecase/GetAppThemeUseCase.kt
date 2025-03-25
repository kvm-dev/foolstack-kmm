package ru.foolstack.settings.api.domain.usecase

import ru.foolstack.settings.api.model.AppThemeDomain

interface GetAppThemeUseCase {
    fun getCurrentAppTheme():AppThemeDomain
}