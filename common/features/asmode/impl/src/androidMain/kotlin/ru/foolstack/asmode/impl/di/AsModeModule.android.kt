package ru.foolstack.asmode.impl.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.foolstack.asmode.api.domain.usecase.GetAsModeUseCase
import ru.foolstack.asmode.impl.data.repository.AsModeRepository
import ru.foolstack.asmode.impl.data.repository.local.LocalDataSource
import ru.foolstack.asmode.impl.data.repository.network.AsModeApi
import ru.foolstack.asmode.impl.data.repository.network.NetworkDataSource
import ru.foolstack.asmode.impl.domain.usecase.GetAsModeUseCaseImpl
import ru.foolstack.asmode.impl.mapper.Mapper

actual val asModeModule: Module
    get() = module {
        single<Mapper> { Mapper() }
        single<AsModeApi> { AsModeApi(client = get())}
        single<LocalDataSource> {LocalDataSource(databaseSdk = get())}
        single<NetworkDataSource> { NetworkDataSource(api = get(), mapper = get())}
        single<AsModeRepository> { AsModeRepository(networkDataSource = get(), localDataSource = get()) }
        single<GetAsModeUseCase> { GetAsModeUseCaseImpl(repository = get()) }
    }