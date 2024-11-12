package ru.foolstack.books.impl.presentation.ui

import ru.foolstack.books.api.model.BooksDomain
import ru.foolstack.language.api.model.LangResultDomain

sealed class BooksViewState {

    data class LoadingState(val lang: LangResultDomain): BooksViewState()

    data class ErrorState(val lang: LangResultDomain): BooksViewState()

    data class SuccessState(
        val isHaveConnection: Boolean,
        val lang: LangResultDomain,
        val books: BooksDomain?,
        val selectedFilters: List<String>,
        val searchKeyWord: String = ""
    ): BooksViewState()
}