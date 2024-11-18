package ru.foolstack.professions.impl.presentation.ui

import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.professions.api.model.ProfessionsDomain

sealed class ProfessionsViewState {

    data class LoadingState(val lang: LangResultDomain): ProfessionsViewState()

    data class ErrorState(val lang: LangResultDomain): ProfessionsViewState()

    data class SuccessState(
        val isHaveConnection: Boolean,
        val lang: LangResultDomain,
        val professions: ProfessionsDomain?
    ): ProfessionsViewState()
}