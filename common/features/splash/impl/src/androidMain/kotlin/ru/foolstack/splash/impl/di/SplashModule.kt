package ru.foolstack.splash.impl.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.foolstack.splash.api.domain.usecase.CheckUpdateUseCase
import ru.foolstack.splash.impl.data.repository.SplashRepository
import ru.foolstack.splash.impl.data.repository.network.NetworkDataSource
import ru.foolstack.splash.impl.data.repository.network.SplashApi
import ru.foolstack.splash.impl.domain.interactor.SplashInteractor
import ru.foolstack.splash.impl.domain.usecase.CheckUpdateUseCaseImpl
import ru.foolstack.splash.impl.mapper.Mapper
import ru.foolstack.splash.impl.presentation.viewmodel.SplashViewModel

actual val splashModule: Module
    get() = module {
    single <Mapper> { Mapper() }
    single <SplashApi> { SplashApi(get())}
    single <NetworkDataSource> {NetworkDataSource(api = get(), mapper = get())}
    single <SplashRepository> { SplashRepository(networkDataSource = get()) }
    single <CheckUpdateUseCase> {CheckUpdateUseCaseImpl(repository = get())}
    single<SplashInteractor> { SplashInteractor(
        getNetworkStateUseCase =  get(),
        getCurrentLanguageUseCase = get(),
        getProfileUseCase = get(),
        getEventsUseCase = get(),
        getTokenFromLocalUseCase = get(),
        authByEmailUseCase = get(),
        authByTokenUseCase = get(),
        confirmAuthAndRegUseCase = get(),
        registrationByEmailUseCase = get(),
        isUserExistUseCase = get(),
        getProfessionsUseCase = get(),
        checkUpdateUseCase = get(),
        authByTokenOfflineLogUseCase = get(),
        browserUtils = get())
    }
    viewModelOf(::SplashViewModel)
}