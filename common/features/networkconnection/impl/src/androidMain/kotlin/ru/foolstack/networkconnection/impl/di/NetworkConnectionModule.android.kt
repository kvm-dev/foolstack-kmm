package ru.foolstack.networkconnection.impl.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.networkconnection.impl.domain.GetNetworkStateUseCaseImpl

actual val networkConnectionModule: Module
    get() =  module {
        single<GetNetworkStateUseCase> { GetNetworkStateUseCaseImpl(get()) }
    }