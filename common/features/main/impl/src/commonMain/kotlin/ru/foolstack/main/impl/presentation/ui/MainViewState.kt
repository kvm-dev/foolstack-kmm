package ru.foolstack.main.impl.presentation.ui


sealed class MainViewState {
    data object AuthorizedClient: MainViewState()
    data object GuestClient: MainViewState()
}