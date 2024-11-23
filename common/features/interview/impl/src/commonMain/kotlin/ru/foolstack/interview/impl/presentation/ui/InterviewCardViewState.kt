package ru.foolstack.interview.impl.presentation.ui

import ru.foolstack.interview.api.model.MaterialDomain
import ru.foolstack.language.api.model.LangResultDomain

sealed class InterviewCardViewState {

    data class Idle(val lang: LangResultDomain) : InterviewCardViewState()

    data class SuccessState(
        val isHaveConnection: Boolean,
        val lang: LangResultDomain,
        val material: MaterialDomain?
    ): InterviewCardViewState()
}