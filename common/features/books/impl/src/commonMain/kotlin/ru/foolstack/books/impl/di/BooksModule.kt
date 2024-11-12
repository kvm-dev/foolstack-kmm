package ru.foolstack.books.impl.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.foolstack.books.api.domain.usecase.GetBooksUseCase
import ru.foolstack.books.impl.data.repository.BooksRepository
import ru.foolstack.books.impl.data.repository.local.LocalDataSource
import ru.foolstack.books.impl.data.repository.network.BooksApi
import ru.foolstack.books.impl.data.repository.network.NetworkDataSource
import ru.foolstack.books.impl.domain.usecase.GetBooksUseCaseImpl
import ru.foolstack.books.impl.mapper.Mapper

expect val booksModule: Module