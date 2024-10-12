package ru.foolstack.books.impl.mapper

import ru.foolstack.books.api.model.BookDomain
import ru.foolstack.books.api.model.BookProfessionDomain
import ru.foolstack.books.api.model.BooksDomain
import ru.foolstack.books.impl.model.BookResponse
import ru.foolstack.storage.model.Book
import ru.foolstack.storage.model.Books
import ru.foolstack.storage.model.ProfessionListItem

class Mapper {

     fun map(response: BookResponse, imageBase64: String):BookDomain{
         val professions = ArrayList<BookProfessionDomain>()
         response.professions.forEach { profession->
             professions.add(BookProfessionDomain(
                 professionId = profession.professionId,
                 professionName = profession.professionName
             ))
         }
        return BookDomain(
            bookId = response.bookId,
            bookName = response.bookName,
            bookDescription = response.bookDescription,
            bookImageUrl = response.bookImageUrl,
            bookImageBase64 = imageBase64,
            bookRefLink = response.bookRefLink,
            bookCostWithoutSale = response.bookCostWithoutSale,
            bookCostWithSale = response.bookCostWithSale,
            professions = professions
        )
    }

    fun map(response: Book):BookDomain{
        val professions = ArrayList<BookProfessionDomain>()
        response.professions.forEach {profession->
            professions.add(BookProfessionDomain(
                professionId = profession.professionId,
                professionName = profession.professionName
            ))
        }
        return BookDomain(
            bookId = response.bookId,
            bookName = response.bookName,
            bookDescription = response.bookDescription,
            bookImageUrl = response.bookImageUrl,
            bookImageBase64 = response.bookImageBase64,
            bookRefLink = response.bookRefLink,
            bookCostWithoutSale = response.bookCostWithoutSale,
            bookCostWithSale = response.bookCostWithSale,
            professions = professions
        )
    }

    fun map(response: BooksDomain): Books {
        val bookList = ArrayList<Book>()
        response.books.forEach { book->
            val professions = ArrayList<ProfessionListItem>()
            book.professions.forEach { profession->
                professions.add(ProfessionListItem(
                    professionId = profession.professionId,
                    professionName = profession.professionName
                ))
            }
            bookList.add(Book(
                bookId = book.bookId,
                bookName = book.bookName,
                bookDescription = book.bookDescription,
                bookImageUrl = book.bookImageUrl,
                bookImageBase64 = book.bookImageBase64,
                bookRefLink = book.bookRefLink,
                bookCostWithSale = book.bookCostWithSale,
                bookCostWithoutSale = book.bookCostWithoutSale,
                professions = professions
            ))
        }
        return Books(
            books = bookList,
            maxSalePercent = response.maxSalePercent,
            prText = response.prText,
            errorMsg = response.errorMsg
        )
    }
}