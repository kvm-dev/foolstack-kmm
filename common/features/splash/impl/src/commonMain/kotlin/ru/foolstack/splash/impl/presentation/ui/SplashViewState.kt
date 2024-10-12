package ru.foolstack.splash.impl.presentation.ui

import ru.foolstack.books.api.model.BooksDomain
import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.interview.api.model.MaterialsDomain
import ru.foolstack.news.api.model.NewsDomain
import ru.foolstack.professions.api.model.ProfessionsDomain
import ru.foolstack.profile.api.model.ProfileDomain
import ru.foolstack.study.api.model.StudiesDomain
import ru.foolstack.tests.api.model.TestsDomain

sealed class SplashViewState{
    data class UnAuthorized(
        val lang: String,
        val isInternetConnected: Boolean,
        val splashBottomText: SplashBottomText
    ): SplashViewState()

    data class Confirmation(
        val lang: String,
        val splashBottomText: SplashBottomText,
        val isUserExist: Boolean
    ): SplashViewState()

    data class AuthorizationOrRegistration(
        val lang: String,
        val splashBottomText: SplashBottomText
    ): SplashViewState()
    data class Authorized(
        val lang: String,
        val isHaveToken: Boolean?,
        val profileData: ProfileDomain?,
        val isInternetConnected: Boolean,
        val books: BooksDomain?,
        val events: EventsDomain?,
        val materials: MaterialsDomain?,
        val news: NewsDomain?,
        val studies: StudiesDomain?,
        val tests: TestsDomain?,
        val professions: ProfessionsDomain?
    ): SplashViewState()

    data class NoConnectionError(
        val splashTitleText: String = "",
        val splashDescriptionText: String = ""
    ): SplashViewState()

    data class AnyError(
        val splashTitleText: String = "",
        val splashDescriptionText: String = "",
        val tryAgainButtonText: String = ""
    ): SplashViewState()
}


data class SplashBottomText(
    val splashTitleText: String = "",
    val splashDescriptionText: String = "",
    val splashMainButtonText: String = "",
    val splashSecondButtonText: String = "",
    val splashResendButtonText: String = ""
)