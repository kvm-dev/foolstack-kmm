package ru.foolstack.impl.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.foolstack.impl.domain.interactor.SplashInteractor
import ru.foolstack.impl.domain.usecase.GetCurrentLanguageUseCaseImpl
import ru.foolstack.impl.domain.usecase.GetNetworkStateUseCaseImpl
import ru.foolstack.impl.mapper.Mapper
import ru.foolstack.impl.presentation.viewmodel.SplashViewModel
import ru.foolstack.splash.api.domain.usecase.GetCurrentLanguageUseCase
import ru.foolstack.splash.api.domain.usecase.GetNetworkStateUseCase

val splashModule = module {
    single<GetNetworkStateUseCase> { GetNetworkStateUseCaseImpl(androidContext()) }
    single<GetCurrentLanguageUseCase> { GetCurrentLanguageUseCaseImpl(Mapper()) }
    single { SplashInteractor(GetCurrentLanguageUseCaseImpl(Mapper()), GetNetworkStateUseCaseImpl(androidContext())) }
    viewModelOf(::SplashViewModel)
}