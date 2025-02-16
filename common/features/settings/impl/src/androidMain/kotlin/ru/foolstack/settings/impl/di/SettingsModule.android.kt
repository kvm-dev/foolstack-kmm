package ru.foolstack.settings.impl.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.foolstack.settings.api.domain.usecase.GetAppThemeUseCase
import ru.foolstack.settings.api.domain.usecase.SetAppThemeUseCase
import ru.foolstack.settings.impl.data.repository.SettingsRepository
import ru.foolstack.settings.impl.data.repository.local.LocalDataSource
import ru.foolstack.settings.impl.domain.interactor.SettingsInteractor
import ru.foolstack.settings.impl.domain.usecase.GetAppThemeUseCaseImpl
import ru.foolstack.settings.impl.domain.usecase.SetAppThemeUseCaseImpl
import ru.foolstack.settings.impl.mapper.Mapper
import ru.foolstack.settings.impl.presentation.viewmodel.SettingsViewModel

actual val settingsModule: Module
    get() = module {
        single<Mapper> { Mapper() }
        single<LocalDataSource> { LocalDataSource(encryptedPreferences = get())}
        single<SettingsRepository> { SettingsRepository(localDataSource = get(), mapper = get()) }
        single<GetAppThemeUseCase> { GetAppThemeUseCaseImpl(repository = get()) }
        single<SetAppThemeUseCase> { SetAppThemeUseCaseImpl(repository = get(), mapper = get()) }
        single<SettingsInteractor> { SettingsInteractor(
            getCurrentLanguageUseCase = get(),
            getNetworkStateUseCase = get(),
            getProfileUseCase = get(),
            updateEmailUseCase = get(),
            updateNameUseCase = get(),
            updatePhotoUseCase = get(),
            deleteProfileUseCase = get(),
            getAppThemeUseCase = get(),
            setAppThemeUseCase = get()
        )}
        viewModelOf(::SettingsViewModel)
    }