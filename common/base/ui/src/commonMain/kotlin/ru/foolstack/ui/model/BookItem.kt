package ru.foolstack.ui.model

data class BookItem(
    val bookId: Int,
    val bookName: String,
    val bookPrice: Int,
    val bookSalePrice: Int,
    val bookImageBase64: String,
    val bookTags: List<String>,
    val bookRefLink: String
)