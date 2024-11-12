package ru.foolstack.events.impl.presentation.ui

import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.language.api.model.LangResultDomain

sealed class EventsViewState {

    data class LoadingState(val lang: LangResultDomain): EventsViewState()

    data class ErrorState(val lang: LangResultDomain): EventsViewState()

    data class SuccessState(
        val isHaveConnection: Boolean,
        val lang: LangResultDomain,
        val events: EventsDomain?,
        val selectedFilters: List<String>
    ): EventsViewState()
}