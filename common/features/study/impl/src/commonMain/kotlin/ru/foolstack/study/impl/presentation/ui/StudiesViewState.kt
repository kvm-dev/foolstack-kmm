package ru.foolstack.study.impl.presentation.ui

import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.study.api.model.StudiesDomain

sealed class StudiesViewState {

    data class LoadingState(val lang: LangResultDomain): StudiesViewState()

    data class ErrorState(val lang: LangResultDomain): StudiesViewState()

    data class SuccessState(
        val isHaveConnection: Boolean,
        val lang: LangResultDomain,
        val studies: StudiesDomain?,
        val selectedFilters: List<String>
    ): StudiesViewState()
}