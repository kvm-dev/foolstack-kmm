package ru.foolstack.books.impl.mapper

import ru.foolstack.books.api.model.BookDomain
import ru.foolstack.books.api.model.BookProfessionDomain
import ru.foolstack.books.api.model.BooksDomain
import ru.foolstack.books.impl.model.BookResponse
import ru.foolstack.events.api.model.EventDomain
import ru.foolstack.events.api.model.EventSubDomain
import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.storage.model.Book
import ru.foolstack.storage.model.Books
import ru.foolstack.storage.model.ProfessionListItem
import ru.foolstack.ui.model.BookItem
import ru.foolstack.ui.model.Chip
import ru.foolstack.ui.model.EventItem
import ru.foolstack.ui.utils.timestampToDateString

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
            subscribeText = response.subscribeText,
            subscribeMinCost = response.subscribeMinCost,
            subscribeLink = response.subscribeLink,
            errorMsg = response.errorMsg
        )
    }

    fun mapToBookItems(booksDomain: BooksDomain?): List<BookItem>{
        val bookList = ArrayList<BookItem>()
        booksDomain?.books?.forEach {book->
            val tags = ArrayList<String>()
            book.professions.forEach {
                tags.add(it.professionName)
            }
            bookList.add(
                BookItem(
                    bookId = book.bookId,
                    bookName = book.bookName,
                    bookPrice = book.bookCostWithoutSale,
                    bookSalePrice = book.bookCostWithSale,
                    bookImageBase64 = book.bookImageBase64,
                    bookTags = tags,
                    bookRefLink = book.bookRefLink
                )
            )
        }
        return bookList
    }

    fun mapToChips(bookList: List<BookDomain>): List<Chip>{
        val list = HashSet<Chip>()
        bookList.forEach { book->
            book.professions.forEach {sub->
                list.add(mapToBookChip(sub))
            }
        }
        return list.toList()
    }

    private fun mapToBookChip(sub: BookProfessionDomain): Chip {
        return Chip(
            id = sub.professionId,
            name = sub.professionName
        )
    }
}