package ru.foolstack.settings.impl.domain.usecase

import ru.foolstack.settings.api.domain.usecase.SetAppThemeUseCase
import ru.foolstack.settings.api.model.AppThemeDomain
import ru.foolstack.settings.impl.data.repository.SettingsRepository
import ru.foolstack.settings.impl.mapper.Mapper

class SetAppThemeUseCaseImpl(private val repository: SettingsRepository, private val mapper: Mapper): SetAppThemeUseCase {
    override fun setCurrentAppTheme(appTheme: AppThemeDomain) {
        repository.setAppTheme(appTheme)
    }
}