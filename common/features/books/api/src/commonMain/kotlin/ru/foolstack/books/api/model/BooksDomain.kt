package ru.foolstack.books.api.model

data class BooksDomain(
    val books: List<BookDomain>,
    val maxSalePercent: Int,
    val prText: String,
    val subscribeText: String,
    val subscribeMinCost: Int,
    val subscribeLink: String,
    val errorMsg: String
)

data class BookDomain(
    val bookId: Int,
    val bookName: String,
    val bookDescription: String,
    val bookImageBase64: String,
    val bookRefLink: String,
    val bookCostWithSale: Int,
    val bookCostWithoutSale: Int,
    val professions: List<BookProfessionDomain>
)

data class BookProfessionDomain(
    val professionId: Int,
    val professionName: String
)