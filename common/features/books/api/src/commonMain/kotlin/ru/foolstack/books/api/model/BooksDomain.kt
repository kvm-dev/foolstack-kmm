package ru.foolstack.books.api.model

data class BooksDomain(
    val books: List<BookDomain>,
    val maxSalePercent: Int,
    val prText: String,
    val errorMsg: String
)

data class BookDomain(
    val bookId: Int,
    val bookName: String,
    val bookDescription: String,
    val bookImageUrl: String,
    val bookImageBase64: String,
    val bookRefLink: String,
    val bookCostWithSale: Int,
    val bookCostWithoutSale: Int,
    val professions: List<Int>
)