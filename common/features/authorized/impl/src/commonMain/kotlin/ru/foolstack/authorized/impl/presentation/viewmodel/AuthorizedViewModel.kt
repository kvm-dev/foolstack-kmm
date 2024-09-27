package ru.foolstack.authorized.impl.presentation.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import ru.foolstack.authorized.impl.domain.interactor.AuthorizedInteractor
import ru.foolstack.authorized.impl.presentation.ui.AuthorizedViewState
import ru.foolstack.viewmodel.BaseViewModel

class AuthorizedViewModel(private val interactor: AuthorizedInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<AuthorizedViewState>(
        AuthorizedViewState.MainScreenState
    )
}