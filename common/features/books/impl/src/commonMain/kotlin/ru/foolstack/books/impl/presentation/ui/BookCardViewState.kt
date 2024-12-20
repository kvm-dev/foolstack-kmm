package ru.foolstack.books.impl.presentation.ui

import ru.foolstack.books.api.model.BookDomain
import ru.foolstack.language.api.model.LangResultDomain

sealed class BookCardViewState {

    data class Idle(val lang: LangResultDomain) : BookCardViewState()

    data class SuccessState(
        val isHaveConnection: Boolean,
        val lang: LangResultDomain,
        val book: BookDomain?
    ): BookCardViewState()
}