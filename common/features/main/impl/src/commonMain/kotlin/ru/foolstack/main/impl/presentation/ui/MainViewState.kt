package ru.foolstack.main.impl.presentation.ui

import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.profile.api.model.ProfileDomain


sealed class MainViewState {

    data object Loading: MainViewState()

    data class ErrorState(
        val errorTitle: String,
        val errorText: String
    ): MainViewState()
    data class AuthorizedClient(
        val isHaveConnection: Boolean,
        val lang: LangResultDomain,
        val profile: ProfileDomain?,
        val events: EventsDomain?
    ): MainViewState()
    data class GuestClient(
        val isHaveConnection: Boolean,
        val lang: LangResultDomain,
        val events: EventsDomain?
    ): MainViewState()
}