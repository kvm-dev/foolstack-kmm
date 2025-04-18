package ru.foolstack.events.impl.presentation.ui

import ru.foolstack.events.api.model.EventDomain
import ru.foolstack.language.api.model.LangResultDomain

sealed class EventCardViewState {

    data class Idle(val lang: LangResultDomain): EventCardViewState()

    data class SuccessState(
        val isHaveConnection: Boolean,
        val lang: LangResultDomain,
        val event: EventDomain?
    ): EventCardViewState()
}