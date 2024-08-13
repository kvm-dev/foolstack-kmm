package ru.foolstack.impl.presentation.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.foolstack.impl.domain.interactor.SplashInteractor
import ru.foolstack.impl.presentation.ui.SplashViewState
import ru.foolstack.viewmodel.BaseViewModel

class SplashViewModel(private val interactor: SplashInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow(SplashViewState(null, null))
    val uiState: StateFlow<SplashViewState> = _uiState.asStateFlow()

    private fun updateUiState(uiState: SplashViewState){
        _uiState.value = uiState
    }


    fun getLocal() = interactor.getCurrentLang()

    fun isConnectionAvailable() = interactor.isConnectionAvailable()

    fun initViewModel() {}
}