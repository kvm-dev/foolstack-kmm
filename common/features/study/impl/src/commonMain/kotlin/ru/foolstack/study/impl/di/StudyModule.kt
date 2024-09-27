package ru.foolstack.study.impl.di

import org.koin.dsl.module
import ru.foolstack.study.api.domain.usecase.GetStudiesUseCase
import ru.foolstack.study.impl.data.repository.StudyRepository
import ru.foolstack.study.impl.data.repository.local.LocalDataSource
import ru.foolstack.study.impl.data.repository.network.NetworkDataSource
import ru.foolstack.study.impl.data.repository.network.StudyApi
import ru.foolstack.study.impl.domain.usecase.GetStudiesUseCaseImpl
import ru.foolstack.study.impl.mapper.Mapper

val studyModule = module {
    single<Mapper> { Mapper() }
    single<StudyApi> { StudyApi(get()) }
    single<LocalDataSource> { LocalDataSource(databaseSdk = get(), mapper = get()) }
    single<NetworkDataSource> { NetworkDataSource(api = get(), mapper = get()) }
    single<StudyRepository> { StudyRepository(
        networkDataSource = get(), localDataSource = get()) }
    single<GetStudiesUseCase> { GetStudiesUseCaseImpl(get())}
}