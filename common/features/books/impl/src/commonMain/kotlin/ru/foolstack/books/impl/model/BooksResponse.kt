package ru.foolstack.books.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BooksResponse(
    @SerialName("books") val books: List<BookResponse> = listOf(),
    @SerialName("maxSalePercent") val maxSalePercent: Int = 0,
    @SerialName("prText") val prText: String = "",
    @SerialName("subscribeText") val subscribeText: String = "",
    @SerialName("subscribeMinCost") val subscribeMinCost: Int = 0,
    @SerialName("subscribeLink") val subscribeLink: String = "",
    @SerialName("errorMsg") val errorMsg: String
)

@Serializable
data class BookResponse(
    @SerialName("bookId") val bookId: Int = 0,
    @SerialName("bookName") val bookName: String = "",
    @SerialName("bookDescription") val bookDescription: String = "",
    @SerialName("bookImage") val bookImage: String = "",
    @SerialName("bookRefLink") val bookRefLink: String = "",
    @SerialName("bookCostWithSale") val bookCostWithSale: Int = 0,
    @SerialName("bookCostWithoutSale") val bookCostWithoutSale: Int = 0,
    @SerialName("professions") val professions: List<BookProfessionResponse> = listOf()
)
@Serializable
data class BookProfessionResponse(
    @SerialName("professionId") val professionId: Int = 0,
    @SerialName("professionName") val professionName: String = ""
)