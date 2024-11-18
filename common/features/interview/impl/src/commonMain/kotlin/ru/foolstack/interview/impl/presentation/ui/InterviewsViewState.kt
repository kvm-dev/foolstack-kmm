package ru.foolstack.interview.impl.presentation.ui

import ru.foolstack.interview.api.model.MaterialDomain
import ru.foolstack.interview.api.model.MaterialsDomain
import ru.foolstack.language.api.model.LangResultDomain

sealed class InterviewsViewState {

    data class LoadingState(val lang: LangResultDomain): InterviewsViewState()

    data class ErrorState(val lang: LangResultDomain): InterviewsViewState()

    data class WithoutProfessionState(val lang: LangResultDomain): InterviewsViewState()

    data class SuccessState(
        val isHaveConnection: Boolean,
        val lang: LangResultDomain,
        val currentProfessionId: Int,
        val materials: List<MaterialDomain>,
        val selectedFilters: List<String>
    ): InterviewsViewState()
}