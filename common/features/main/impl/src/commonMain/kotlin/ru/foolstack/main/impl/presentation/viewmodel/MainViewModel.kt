package ru.foolstack.main.impl.presentation.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import ru.foolstack.main.impl.domain.interactor.MainInteractor
import ru.foolstack.main.impl.presentation.ui.MainViewState
import ru.foolstack.viewmodel.BaseViewModel


class MainViewModel(private val interactor: MainInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<MainViewState>(
        MainViewState.GuestClient
    )
}