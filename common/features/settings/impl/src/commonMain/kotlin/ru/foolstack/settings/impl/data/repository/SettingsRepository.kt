package ru.foolstack.settings.impl.data.repository

import ru.foolstack.settings.api.model.AppThemeDomain
import ru.foolstack.settings.impl.data.repository.local.LocalDataSource
import ru.foolstack.settings.impl.mapper.Mapper

class SettingsRepository(private val localDataSource: LocalDataSource, private val mapper: Mapper) {
    fun getAppTheme(): AppThemeDomain{
       return mapper.mapAppThemeToDomain(localDataSource.getAppTheme())
    }

    fun setAppTheme(appTheme: AppThemeDomain){
        localDataSource.setAppTheme(mapper.mapAppThemeFromDomain(appTheme))
    }
}