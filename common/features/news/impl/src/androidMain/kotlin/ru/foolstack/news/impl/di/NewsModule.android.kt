package ru.foolstack.news.impl.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.foolstack.news.api.domain.usecase.GetNewsUseCase
import ru.foolstack.news.impl.data.repository.NewsRepository
import ru.foolstack.news.impl.data.repository.local.LocalDataSource
import ru.foolstack.news.impl.data.repository.network.NetworkDataSource
import ru.foolstack.news.impl.data.repository.network.NewsApi
import ru.foolstack.news.impl.domain.interactor.NewsCardInteractor
import ru.foolstack.news.impl.domain.interactor.NewsInteractor
import ru.foolstack.news.impl.domain.usecase.GetNewsUseCaseImpl
import ru.foolstack.news.impl.mapper.Mapper
import ru.foolstack.news.impl.presentation.viewmodel.NewsViewModel
import ru.foolstack.news.impl.presentation.viewmodel.NewsCardViewModel


actual val newsModule: Module
    get() = module{
            single<Mapper> { Mapper() }
    single<NewsApi> { NewsApi(get()) }
    single<NetworkDataSource> { NetworkDataSource(api = get(), mapper = get()) }
    single<LocalDataSource> { LocalDataSource(databaseSdk = get(), mapper = get()) }
    single<NewsRepository> { NewsRepository(
        networkDataSource = get(), localDataSource = get()) }
    single<GetNewsUseCase> { GetNewsUseCaseImpl(get()) }
    single<NewsInteractor> { NewsInteractor(
        getCurrentLanguageUseCase = get(),
        getNetworkStateUseCase = get(),
        getNewsUseCase = get()
    )
    }
        single<NewsCardInteractor> { NewsCardInteractor(
            getCurrentLanguageUseCase = get(),
            getNetworkStateUseCase = get(),
            getNewsUseCase = get(),
            shareUtils = get()
        )
        }
        viewModelOf(::NewsViewModel)
        viewModelOf(::NewsCardViewModel)
    }