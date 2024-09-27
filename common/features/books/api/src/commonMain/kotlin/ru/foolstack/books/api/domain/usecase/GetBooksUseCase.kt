package ru.foolstack.books.api.domain.usecase

import ru.foolstack.books.api.model.BooksDomain

interface GetBooksUseCase {

    suspend fun getBooks(fromLocal: Boolean = false):BooksDomain
}