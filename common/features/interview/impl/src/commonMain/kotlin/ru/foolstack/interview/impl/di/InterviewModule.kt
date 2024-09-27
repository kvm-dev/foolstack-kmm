package ru.foolstack.interview.impl.di

import org.koin.dsl.module
import ru.foolstack.interview.api.domain.usecase.GetMaterialsUseCase
import ru.foolstack.interview.impl.data.repository.MaterialsRepository
import ru.foolstack.interview.impl.data.repository.local.LocalDataSource
import ru.foolstack.interview.impl.data.repository.network.MaterialsApi
import ru.foolstack.interview.impl.data.repository.network.NetworkDataSource
import ru.foolstack.interview.impl.domain.usecase.GetMaterialsUseCaseImpl
import ru.foolstack.interview.impl.mapper.Mapper

val interviewModule = module {
    single<Mapper> { Mapper() }
    single<LocalDataSource> { LocalDataSource(databaseSdk = get(), mapper = get()) }
    single<MaterialsApi> { MaterialsApi(get()) }
    single<NetworkDataSource> { NetworkDataSource(api = get(), mapper = get()) }
    single<MaterialsRepository> { MaterialsRepository(
        localDataSource = get(),
        networkDataSource = get()) }
    single<GetMaterialsUseCase> { GetMaterialsUseCaseImpl(get())}
}