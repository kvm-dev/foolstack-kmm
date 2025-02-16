package ru.foolstack.settings.impl.domain.usecase

import ru.foolstack.settings.api.domain.usecase.GetAppThemeUseCase
import ru.foolstack.settings.api.model.AppThemeDomain
import ru.foolstack.settings.impl.data.repository.SettingsRepository

class GetAppThemeUseCaseImpl(private val repository: SettingsRepository):GetAppThemeUseCase {
    override fun getCurrentAppTheme(): AppThemeDomain {
        return repository.getAppTheme()
    }
}