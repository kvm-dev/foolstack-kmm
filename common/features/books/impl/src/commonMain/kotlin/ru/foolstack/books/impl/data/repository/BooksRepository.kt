package ru.foolstack.books.impl.data.repository

import ru.foolstack.books.api.model.BooksDomain
import ru.foolstack.books.impl.data.repository.local.LocalDataSource
import ru.foolstack.books.impl.data.repository.network.NetworkDataSource

class BooksRepository(private val networkDataSource: NetworkDataSource, private val localDataSource: LocalDataSource) {
    suspend fun getBooksFromServer():BooksDomain{
        val response = networkDataSource.getBooks()
        return if(response.errorMsg.isNotEmpty()){
            return response
        }
        else{
            localDataSource.saveBooks(response)
            localDataSource.getBooks()
        }
        }

    suspend fun getBooksFromLocal(): BooksDomain = localDataSource.getBooks()
}