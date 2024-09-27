package ru.foolstack.splash.impl.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.foolstack.splash.impl.domain.interactor.SplashInteractor
import ru.foolstack.splash.impl.mapper.Mapper
import ru.foolstack.splash.impl.presentation.viewmodel.SplashViewModel

val splashModule = module {
    single <Mapper> { Mapper() }
    single<SplashInteractor> { SplashInteractor(
        getNetworkStateUseCase =  get(),
        getCurrentLanguageUseCase = get(),
        getProfileUseCase = get(),
        getBooksUseCase = get(),
        getEventsUseCase = get(),
        getMaterialsUseCase = get(),
        getNewsUseCase = get(),
        getStudiesUseCase = get(),
        getTestsUseCase = get(),
        getTokenFromLocalUseCase = get(),
        authByEmailUseCase = get(),
        authByTokenUseCase = get(),
        confirmAuthAndRegUseCase = get(),
        registrationByEmailUseCase = get(),
        isUserExistUseCase = get())
    }
    viewModelOf(::SplashViewModel)
}