package ru.foolstack.books.impl.data.repository.network

object BooksEndpoints {
    val getBooks: String
        get() = "books/get-books/"

    val getBooksVersion: String
        get() = "books/get-version/"
}