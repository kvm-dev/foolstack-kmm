package ru.foolstack.android.splash.impl.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {
//    single { SplashInteractor(getLocalUseCase = GetLocalUseCaseImpl(), getNetworkConnectionUseCase = GetNetworkConnectionUseCaseImpl(get()), getUserTokenUseCase = GetTokenUseCaseImpl(get()), addTokenUseCase = AddTokenUseCaseImpl(get()
//    ), updateTokenUseCase = UpdateTokenUseCaseImpl(get()), clearTokensUseCase = ClearTokensUseCaseImpl(get())) }
    viewModel { SplashViewModel(get()) }
}