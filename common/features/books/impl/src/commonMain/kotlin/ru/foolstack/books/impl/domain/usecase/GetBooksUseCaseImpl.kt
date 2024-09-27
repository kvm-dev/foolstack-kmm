package ru.foolstack.books.impl.domain.usecase

import ru.foolstack.books.api.domain.usecase.GetBooksUseCase
import ru.foolstack.books.api.model.BooksDomain
import ru.foolstack.books.impl.data.repository.BooksRepository

class GetBooksUseCaseImpl(private val repository: BooksRepository):GetBooksUseCase {
    override suspend fun getBooks(fromLocal:Boolean): BooksDomain {
        return if(fromLocal){
            repository.getBooksFromLocal()
        }
        else{
            repository.getBooksFromServer()
        }
    }
}