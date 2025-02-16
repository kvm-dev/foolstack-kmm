package ru.foolstack.main.impl.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.foolstack.main.impl.domain.interactor.MainInteractor
import ru.foolstack.main.impl.presentation.viewmodel.MainViewModel

actual val mainModule: Module
    get() = module {
    single<MainInteractor> {
        MainInteractor(getCurrentLanguageUseCase = get(),
            getNetworkStateUseCase = get(),
            getProfileUseCase = get(),
            getEventsUseCase = get(),
            getTokenFromLocalUseCase = get(),
            logoutUseCase = get(),
            getAsModeUseCase = get(),
            getBooksUseCase = get(),
            getTestsUseCase = get(),
            getMaterialsUseCase = get(),
            getNewsUseCase = get(),
            getStudiesUseCase = get())
            }
    viewModelOf(::MainViewModel)
}
