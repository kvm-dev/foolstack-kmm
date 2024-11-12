package ru.foolstack.books.impl.data.repository.network

import ru.foolstack.books.api.model.BookDomain
import ru.foolstack.books.api.model.BooksDomain
import ru.foolstack.books.impl.mapper.Mapper
import ru.foolstack.network.utils.getBase64Bitmap

class NetworkDataSource(private val api: BooksApi, private val mapper: Mapper){

    suspend fun getBooks(): BooksDomain {
        val result = api.getBooks()
        val bookList = ArrayList<BookDomain>()
        result.books.forEach {book->
            var bookBase64Image = ""
            if(book.bookImageUrl.isNotEmpty()){
                bookBase64Image= getBase64Bitmap(book.bookImageUrl)
            }
            bookList.add(mapper.map(book, bookBase64Image))
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
}