package ru.foolstack.books.impl.di

import org.koin.dsl.module
import ru.foolstack.books.api.domain.usecase.GetBooksUseCase
import ru.foolstack.books.impl.data.repository.BooksRepository
import ru.foolstack.books.impl.data.repository.local.LocalDataSource
import ru.foolstack.books.impl.data.repository.network.BooksApi
import ru.foolstack.books.impl.data.repository.network.NetworkDataSource
import ru.foolstack.books.impl.domain.usecase.GetBooksUseCaseImpl
import ru.foolstack.books.impl.mapper.Mapper

val booksModule = module {
    single<Mapper> { Mapper() }
    single<BooksApi> { BooksApi(get()) }
    single<NetworkDataSource> { NetworkDataSource(api = get(), mapper = get()) }
    single<LocalDataSource> { LocalDataSource(databaseSdk = get(), mapper = get()) }
    single<BooksRepository> { BooksRepository(
        networkDataSource = get(), localDataSource = get()) }
    single<GetBooksUseCase> { GetBooksUseCaseImpl(get())}
}