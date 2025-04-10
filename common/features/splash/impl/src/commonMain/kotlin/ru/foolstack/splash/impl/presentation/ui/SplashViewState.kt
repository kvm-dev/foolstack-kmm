package ru.foolstack.splash.impl.presentation.ui

import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.profile.api.model.ProfileDomain

sealed class SplashViewState{

    data object Idle: SplashViewState()
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
        val events: EventsDomain?,
    ): SplashViewState()

    data class NoConnectionError(
        val splashTitleText: String = "",
        val splashDescriptionText: String = "",
        val lang: String
    ): SplashViewState()

    data class AnyError(
        val splashTitleText: String = "",
        val splashDescriptionText: String = "",
        val tryAgainButtonText: String = "",
        val lang: String
    ): SplashViewState()

    data class NeedUpdateState(
        val titleText: String,
        val descriptionText:String,
        val isCanClose: Boolean,
        val generalButtonText: String,
        val secondaryButtonText: String,
        val lang: String
    ): SplashViewState()
}


data class SplashBottomText(
    val splashTitleText: String = "",
    val splashDescriptionText: String = "",
    val splashMainButtonText: String = "",
    val splashSecondButtonText: String = "",
    val splashResendButtonText: String = ""
)