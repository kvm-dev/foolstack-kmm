package ru.foolstack.books.api.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import ru.foolstack.books.api.model.BooksDomain
import ru.foolstack.utils.model.ResultState

interface GetBooksUseCase {

    val booksState: StateFlow<ResultState<BooksDomain>>
    suspend fun getBooks(fromLocal: Boolean = false):BooksDomain
}