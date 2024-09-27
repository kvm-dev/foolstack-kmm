package ru.foolstack.news.impl.di

import org.koin.dsl.module
import ru.foolstack.news.api.domain.usecase.GetNewsUseCase
import ru.foolstack.news.impl.data.repository.NewsRepository
import ru.foolstack.news.impl.data.repository.local.LocalDataSource
import ru.foolstack.news.impl.data.repository.network.NetworkDataSource
import ru.foolstack.news.impl.data.repository.network.NewsApi
import ru.foolstack.news.impl.domain.usecase.GetNewsUseCaseImpl
import ru.foolstack.news.impl.mapper.Mapper

val newsModule = module {
    single<Mapper> { Mapper() }
    single<NewsApi> { NewsApi(get()) }
    single<NetworkDataSource> { NetworkDataSource(api = get(), mapper = get()) }
    single<LocalDataSource> { LocalDataSource(databaseSdk = get(), mapper = get()) }
    single<NewsRepository> { NewsRepository(
        networkDataSource = get(), localDataSource = get()) }
    single<GetNewsUseCase> { GetNewsUseCaseImpl(get())}
}