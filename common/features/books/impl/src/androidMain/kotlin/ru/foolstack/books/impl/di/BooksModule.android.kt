package ru.foolstack.books.impl.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.foolstack.books.api.domain.usecase.GetBooksUseCase
import ru.foolstack.books.impl.data.repository.BooksRepository
import ru.foolstack.books.impl.data.repository.local.LocalDataSource
import ru.foolstack.books.impl.data.repository.network.BooksApi
import ru.foolstack.books.impl.data.repository.network.NetworkDataSource
import ru.foolstack.books.impl.domain.interactor.BookCardInteractor
import ru.foolstack.books.impl.domain.interactor.BooksInteractor
import ru.foolstack.books.impl.domain.usecase.GetBooksUseCaseImpl
import ru.foolstack.books.impl.mapper.Mapper
import ru.foolstack.books.impl.presentation.viewmodel.BooksViewModel
import ru.foolstack.books.impl.presentation.viewmodel.BookCardViewModel

actual val booksModule: Module
    get() = module {
        single<Mapper> { Mapper() }
        single<BooksApi> { BooksApi(get()) }
        single<NetworkDataSource> { NetworkDataSource(
            api = get(),
            mapper = get()) }
        single<LocalDataSource> { LocalDataSource(
            databaseSdk = get(),
            mapper = get()) }
        single<BooksRepository> { BooksRepository(
            networkDataSource = get(),
            localDataSource = get()) }
        single<GetBooksUseCase> { GetBooksUseCaseImpl(get()) }
        single<BooksInteractor>{
            BooksInteractor(
                getCurrentLanguageUseCase = get(),
                getNetworkStateUseCase = get(),
                getBooksUseCase = get(),
                browserUtils = get())
        }

        single<BookCardInteractor>{
            BookCardInteractor(
                getCurrentLanguageUseCase = get(),
                getNetworkStateUseCase = get(),
                browserUtils = get(),
                getBooksUseCase = get())
        }
        viewModelOf(::BookCardViewModel)
        viewModelOf(::BooksViewModel)
    }
