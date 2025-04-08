package ru.foolstack.books.impl.data.repository.network

import ru.foolstack.books.api.model.BookDomain
import ru.foolstack.books.api.model.BooksDomain
import ru.foolstack.books.impl.mapper.Mapper
import ru.foolstack.books.impl.model.BooksVersionResponse

class NetworkDataSource(private val api: BooksApi, private val mapper: Mapper){

    suspend fun getBooks(): BooksDomain {
        val result = api.getBooks()
        val bookList = ArrayList<BookDomain>()
        result.books.forEach {book->
            bookList.add(mapper.map(book))
        }

       return BooksDomain(
           books = bookList,
           maxSalePercent = result.maxSalePercent,
           prText = result.prText,
           subscribeText = result.subscribeText,
           subscribeMinCost = result.subscribeMinCost,
           subscribeLink = result.subscribeLink,
           errorMsg = result.errorMsg
       )
    }

    suspend fun getVersion():BooksVersionResponse{
        return api.getVersion()
    }
}