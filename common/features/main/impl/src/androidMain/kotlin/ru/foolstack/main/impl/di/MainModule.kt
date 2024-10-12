package ru.foolstack.main.impl.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.foolstack.main.impl.domain.interactor.MainInteractor
import ru.foolstack.main.impl.presentation.viewmodel.MainViewModel

val mainModule = module {
    single<MainInteractor> {
        MainInteractor(getCurrentLanguageUseCase = get(),
            getNetworkStateUseCase = get(),
            getProfileUseCase = get(),
            getEventsUseCase = get(),
            getTokenFromLocalUseCase = get())}
    viewModelOf(::MainViewModel)
}