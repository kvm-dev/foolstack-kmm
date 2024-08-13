package ru.foolstack.authorization.impl.presentation.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.foolstack.authorization.impl.presentation.ui.AuthorizationViewState
import ru.foolstack.viewmodel.BaseViewModel

class AuthorizationViewModel() : BaseViewModel() {
    private val _uiState = MutableStateFlow(AuthorizationViewState(null, null))
    val uiState: StateFlow<AuthorizationViewState> = _uiState.asStateFlow()

    private fun updateUiState(uiState: AuthorizationViewState){
        _uiState.value = uiState
    }

    private fun getLocal() = "RU"

    fun initViewModel() {}
}