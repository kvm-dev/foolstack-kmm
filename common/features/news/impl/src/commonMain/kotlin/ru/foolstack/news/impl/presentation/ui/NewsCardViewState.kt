package ru.foolstack.news.impl.presentation.ui

import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.news.api.model.SingleNewsDomain

sealed class NewsCardViewState {

    data class Idle(val lang: LangResultDomain): NewsCardViewState()

    data class SuccessState(
        val isHaveConnection: Boolean,
        val lang: LangResultDomain,
        val singleNewsDomain: SingleNewsDomain?
    ): NewsCardViewState()
}