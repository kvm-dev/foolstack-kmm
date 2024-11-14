package ru.foolstack.news.impl.presentation.ui

import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.news.api.model.NewsDomain

sealed class NewsViewState {

    data class LoadingState(val lang: LangResultDomain): NewsViewState()

    data class ErrorState(val lang: LangResultDomain): NewsViewState()

    data class SuccessState(
        val isHaveConnection: Boolean,
        val lang: LangResultDomain,
        val news: NewsDomain?
    ): NewsViewState()
}