package ru.foolstack.professions.impl.di

import org.koin.dsl.module
import ru.foolstack.professions.api.domain.usecase.GetProfessionsUseCase
import ru.foolstack.professions.impl.data.repository.ProfessionsRepository
import ru.foolstack.professions.impl.data.repository.local.LocalDataSource
import ru.foolstack.professions.impl.data.repository.network.NetworkDataSource
import ru.foolstack.professions.impl.data.repository.network.ProfessionsApi
import ru.foolstack.professions.impl.domain.usecase.GetProfessionsUseCaseImpl
import ru.foolstack.professions.impl.mapper.Mapper

val professionsModule = module {
    single<Mapper> { Mapper() }
    single<ProfessionsApi> { ProfessionsApi(get()) }
    single<NetworkDataSource> { NetworkDataSource(api = get(), mapper = get()) }
    single<LocalDataSource> { LocalDataSource(databaseSdk = get(), mapper = get()) }
    single<ProfessionsRepository> { ProfessionsRepository(
        networkDataSource = get(), localDataSource = get()) }
    single<GetProfessionsUseCase> { GetProfessionsUseCaseImpl(get())}
}