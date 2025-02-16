package ru.foolstack.settings.impl.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.model.ProgressState
import ru.foolstack.settings.impl.domain.interactor.SettingsInteractor
import ru.foolstack.settings.impl.presentation.ui.SettingsViewState
import ru.foolstack.utils.TextFieldValidation
import ru.foolstack.utils.model.ResultState
import ru.foolstack.viewmodel.BaseViewModel

class SettingsViewModel(private val interactor: SettingsInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<SettingsViewState>(
        SettingsViewState.LoadingState(lang = interactor.getCurrentLang())
    )

    val uiState: StateFlow<SettingsViewState> = _uiState.asStateFlow()

    val isAutoThemeEnabled by mutableStateOf(true)
    val isDarkThemeEnabled by mutableStateOf(false)

    var emailValue by mutableStateOf("")
        private set

    var photo by mutableStateOf("")
        private set
    var emailError by mutableStateOf("")
        private set

    var emailIsLoading by mutableStateOf(false)
        private set

    var userNameValue by mutableStateOf("")
        private set
    var userNameError by mutableStateOf("")
        private set

    var userNameIsLoading by mutableStateOf(false)
        private set

    fun initViewModel() = with(viewModelScope + coroutineExceptionHandler) {
        if (progressState.value == ProgressState.LOADING) {
            launch {
                if (interactor.profileState.value !is ResultState.Success) {
                    if (interactor.isConnectionAvailable()) {
                        interactor.getProfileFromServer()
                    } else {
                        interactor.getProfileFromLocal()
                    }
                }
                interactor.profileState.collect { profileState ->
                    if (profileState == ResultState.Idle) {
                        if (interactor.isConnectionAvailable()) {
                            interactor.getProfileFromServer()
                        } else {
                            interactor.getProfileFromLocal()
                        }
                    }
                    val state = interactor.checkState(
                        state = profileState
                    )
                    if (state is SettingsViewState.SuccessState) {
                        val name = state.userName.ifEmpty {
                            if (state.lang is LangResultDomain.Ru) {
                                "Ноунейм"
                            } else {
                                "No-name"
                            }
                        }
                        setUserName(name)
                        setEmail(state.userEmail)
                    }
                    _uiState.update {
                        state
                    }
                    updateState(ProgressState.COMPLETED)
                }
            }
        }
    }

    private fun validateEmail(): Boolean {
        val lang = interactor.getCurrentLang().lang
        val email = emailValue.trim()
        var isValid = true
        var errorMessage = ""
        if (email.isBlank() || email.isEmpty()) {
            errorMessage =
                interactor.getErrorEmailEmpty(lang = lang)
            isValid = false
        } else if (!TextFieldValidation.validateEmail(email)) {
            errorMessage =
                interactor.getErrorEmailIncorrect(lang = lang)
            isValid = false
        }
        emailError = errorMessage
        return isValid
    }

    fun setEmail(value: String) {
        emailValue = value
        emailIsLoading = false
        emailError = ""
        validateEmail()
    }

    fun setUserName(value: String) {
        userNameValue = value
        userNameIsLoading = false
        userNameError = ""
        validateUserName()
    }

    private fun validateUserName(): Boolean {
        val lang = interactor.getCurrentLang().lang
        var isValid = true
        var errorMessage = ""
        if (userNameValue.isBlank() || userNameValue.isEmpty() || userNameValue.length < 2) {
            errorMessage =
                interactor.getErrorUserNameEmpty(lang = lang)
            isValid = false
        }
        userNameError = errorMessage
        return isValid
    }

    fun setAppTheme(theme: String) {
        val current = uiState.value as SettingsViewState.SuccessState
        _uiState.update { current.copy(appTheme = interactor.checkTheme(theme)) }
        interactor.setTheme(theme)
    }

    fun updateUserData() = with(viewModelScope + coroutineExceptionHandler) {
        val current = uiState.value as SettingsViewState.SuccessState
        var withUserName = false
        var withEmail = false
        val successState = uiState.value as SettingsViewState.SuccessState
        if (userNameValue != successState.userName) {
            withUserName = true
        }
        if (emailValue != successState.userEmail) {
            withEmail = true
        }
        if (withUserName && withEmail) {
            if (interactor.isConnectionAvailable()) {
                launch {
                    val result =
                        interactor.updateNameAndEmail(userName = userNameValue, email = emailValue)
                    if (result) {
                        _uiState.update {
                            current.copy(
                                isShowErrorDialog = false,
                                isShowSuccessDialog = true
                            )
                        }
                    } else {
                        _uiState.update {
                            current.copy(
                                isShowSuccessDialog = false,
                                isShowErrorDialog = true
                            )
                        }
                    }
                }
            } else {
                _uiState.update {
                    current.copy(
                        isShowSuccessDialog = false,
                        isShowErrorDialog = false
                    )
                }
            }
        } else if (withUserName && !withEmail) {
            if (interactor.isConnectionAvailable()) {
                launch {
                    val result = interactor.updateUserName(userName = userNameValue)
                    if (result.success) {
                        _uiState.update {
                            current.copy(
                                isShowErrorDialog = false,
                                isShowSuccessDialog = true
                            )
                        }
                    } else {
                        _uiState.update {
                            current.copy(
                                isShowSuccessDialog = false,
                                isShowErrorDialog = true
                            )
                        }
                    }
                }
            } else {
                _uiState.update {
                    current.copy(
                        isShowSuccessDialog = false,
                        isShowErrorDialog = false
                    )
                }
            }

        } else if (!withUserName && withEmail) {
            if (interactor.isConnectionAvailable()) {
                launch {
                    val result = interactor.updateEmail(email = emailValue)
                    if (result.success) {
                        _uiState.update {
                            current.copy(
                                isShowErrorDialog = false,
                                isShowSuccessDialog = true
                            )
                        }
                    } else {
                        _uiState.update {
                            current.copy(
                                isShowSuccessDialog = false,
                                isShowErrorDialog = true
                            )
                        }
                    }
                }
            } else {
                _uiState.update {
                    current.copy(
                        isShowSuccessDialog = false,
                        isShowErrorDialog = false
                    )
                }
            }
        } else {
            _uiState.update {
                current.copy(
                    isShowSuccessDialog = false,
                    isShowErrorDialog = false
                )
            }
        }
    }


    fun updateProfile() = with(viewModelScope + coroutineExceptionHandler) {
        val current = uiState.value as SettingsViewState.SuccessState
        if (interactor.isConnectionAvailable()) {
            launch {
                interactor.getProfileFromServer()
            }
        } else {
            _uiState.update {
                current.copy(
                    isShowSuccessDialog = false,
                    isShowErrorDialog = true
                )
            }
        }
    }

    fun hideStateDialogs() {
        val current = uiState.value as SettingsViewState.SuccessState
        _uiState.update {
            current.copy(
                isShowSuccessDialog = false,
                isShowErrorDialog = false
            )
        }
    }

    fun getCurrentLang() = interactor.getCurrentLang()

    fun getConfirmChangeEmailDialogTitle() = interactor.getConfirmChangeEmailDialogTitle()

    fun getConfirmChangeEmailDialogText() = interactor.getConfirmChangeEmailDialogText()

    fun getConfirmChangeEmailDialogActionBtn() = interactor.getConfirmChangeEmailDialogActionBtn()

    fun getConfirmChangeEmailDialogCancelBtn() = interactor.getConfirmChangeEmailDialogCancelBtn()

    fun getConfirmDeleteAccountDialogTitle() = interactor.getConfirmDeleteAccountDialogTitle()

    fun getConfirmDeleteAccountDialogText() = interactor.getConfirmDeleteAccountDialogText()

    fun getConfirmDeleteAccountDialogActionBtn() =
        interactor.getConfirmDeleteAccountDialogActionBtn()

    fun getConfirmDeleteAccountDialogCancelBtn() =
        interactor.getConfirmDeleteAccountDialogCancelBtn()

    fun getSuccessDialogTitle() = interactor.getSuccessDialogTitle()

    fun getSuccessDialogText() = interactor.getSuccessDialogText()

    fun getSuccessDialogBtn() = interactor.getSuccessDialogBtn()

    fun getFailDialogTitle() = interactor.getFailDialogTitle()

    fun getFailDialogText() = interactor.getFailDialogText()

    fun getFailDialogBtn() = interactor.getFailDialogBtn()

    fun getNoConnectionDialogTitle() = interactor.getNoConnectionDialogTitle()

    fun getNoConnectionDialogText() = interactor.getNoConnectionDialogText()

    fun getNoConnectionDialogBtn() = interactor.getNoConnectionDialogBtn()

    fun isConnectionAvailable() = interactor.isConnectionAvailable()

    fun updatePhoto(photo: ByteArray) = with(viewModelScope + coroutineExceptionHandler) {
        val current = uiState.value as SettingsViewState.SuccessState
        if (isConnectionAvailable()) {
            launch {
                val result = interactor.updatePhoto(photo)
                if (result.success) {
                    _uiState.update {
                        current.copy(
                            isShowSuccessDialog = true,
                            isShowErrorDialog = false
                        )
                    }
                }
                else{
                    _uiState.update {
                        current.copy(
                            isShowSuccessDialog = false,
                            isShowErrorDialog = true
                        )
                    }
                }
            }
        }
    }

    fun deleteProfile() = with(viewModelScope + coroutineExceptionHandler) {
        val current = uiState.value as SettingsViewState.SuccessState
        if (isConnectionAvailable()) {
            launch {
                val result = interactor.deleteProfile()
                if (result.success) {
                    _uiState.update {
                        current.copy(
                            isShowSuccessDialog = true,
                            isShowErrorDialog = false
                        )
                    }
                }
                else{
                    _uiState.update {
                        current.copy(
                            isShowSuccessDialog = false,
                            isShowErrorDialog = true
                        )
                    }
                }
            }
        }
    }
}