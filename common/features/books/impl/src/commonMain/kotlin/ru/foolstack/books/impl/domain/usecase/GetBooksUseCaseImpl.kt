package ru.foolstack.books.impl.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.foolstack.books.api.domain.usecase.GetBooksUseCase
import ru.foolstack.books.api.model.BooksDomain
import ru.foolstack.books.impl.data.repository.BooksRepository
import ru.foolstack.utils.model.ResultState

class GetBooksUseCaseImpl(private val repository: BooksRepository):GetBooksUseCase {

    private val _books = MutableStateFlow<ResultState<BooksDomain>>(
        ResultState.Idle)
    override val booksState = _books.asStateFlow()
    override suspend fun getBooks(fromLocal:Boolean): BooksDomain {
        _books.tryEmit(ResultState.Loading)
        return if(fromLocal){
            val cachedBooks = repository.getBooksFromLocal()
            _books.tryEmit(ResultState.Success(cachedBooks))
            cachedBooks
        }
        else{
            val responseBooks = repository.getBooksFromServer()
            _books.tryEmit(ResultState.Success(responseBooks))
            responseBooks
        }
    }
}