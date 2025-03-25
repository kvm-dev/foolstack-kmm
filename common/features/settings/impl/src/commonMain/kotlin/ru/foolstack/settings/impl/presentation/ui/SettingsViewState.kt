package ru.foolstack.settings.impl.presentation.ui

import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.settings.api.model.AppThemeDomain

sealed class SettingsViewState {

    data class LoadingState(val lang: LangResultDomain): SettingsViewState()

    data class ErrorState(val lang: LangResultDomain): SettingsViewState()

    data class SuccessState(
        val isHaveConnection: Boolean,
        val lang: LangResultDomain,
        val userName: String,
        val userEmail:String,
        val userPhoto: String,
        val appTheme: AppThemeDomain,
        val isShowSuccessDialog: Boolean = false,
        val isShowErrorDialog: Boolean = false
    ): SettingsViewState()
}