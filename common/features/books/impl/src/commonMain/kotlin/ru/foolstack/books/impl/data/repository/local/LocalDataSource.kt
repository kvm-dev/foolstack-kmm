package ru.foolstack.books.impl.data.repository.local

import ru.foolstack.books.api.model.BookDomain
import ru.foolstack.books.api.model.BooksDomain
import ru.foolstack.books.impl.mapper.Mapper
import ru.foolstack.storage.DatabaseSdk

class LocalDataSource(private val databaseSdk: DatabaseSdk, private val mapper: Mapper) {
    suspend fun getBooks():BooksDomain {
        val books = databaseSdk.getBooks()
        val booksList = arrayListOf<BookDomain>()
        books.books.forEach { book->
            booksList.add(mapper.map(book))
        }
        return BooksDomain(
            books = booksList,
            maxSalePercent = books.maxSalePercent,
            prText = books.prText,
            errorMsg = books.errorMsg
        )
    }
    suspend fun saveBooks(books:BooksDomain){
        databaseSdk.saveBooks(mapper.map(books))
    }
}