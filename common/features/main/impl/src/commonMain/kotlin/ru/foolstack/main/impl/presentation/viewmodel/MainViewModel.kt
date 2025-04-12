package ru.foolstack.main.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.foolstack.main.impl.domain.interactor.MainInteractor
import ru.foolstack.main.impl.presentation.ui.MainViewState
import ru.foolstack.model.ProgressState
import ru.foolstack.utils.model.ResultState
import ru.foolstack.viewmodel.BaseViewModel


class MainViewModel(private val interactor: MainInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<MainViewState>(
        MainViewState.Loading(lang = interactor.getCurrentLang())
    )

    val uiState: StateFlow<MainViewState> = _uiState.asStateFlow()

    var asMode = false

    fun initViewModel() {
        if (progressState.value == ProgressState.LOADING) {
            viewModelScope.launch(coroutineExceptionHandler) {
                if(interactor.eventsState.value !is ResultState.Success || interactor.eventsState.value !is ResultState.Loading){
                    if (interactor.isConnectionAvailable()) {
                        interactor.getEventsFromServer()
                    } else {
                        interactor.getEventsFromLocal()
                    }
                }
            }
            viewModelScope.launch(coroutineExceptionHandler + Dispatchers.IO + supervisorJob){
                launch {
                    asMode = interactor.isAsModeActive()
                }
                interactor.eventsState.collect { eventsState ->
                    if(eventsState is ResultState.Success){
                        interactor.profileState.collect { profileState ->
                            if(profileState is ResultState.Success){
                                val state = interactor.checkState(eventsState = eventsState, profileState = profileState)
                                if(state !is MainViewState.Loading){
                                    _uiState.update {
                                        state
                                    }
                                    updateState(ProgressState.COMPLETED)
                                }
                            }
                            else{
                                updateState(ProgressState.LOADING)
                            }
                        }
                    }
                    else{
                        updateState(ProgressState.LOADING)
                    }
                }
            }
        }
    }

    fun navigateToEvent(navController: NavController, eventId: Int, eventDestination: String) {
        val route = "$eventDestination/{eventId}"
        navController.navigate(
            route.replace(
                oldValue = "{eventId}",
                newValue = eventId.toString()
            )
        )
    }

    fun logout() = with(viewModelScope + coroutineExceptionHandler) {
        launch {
            val current = uiState.value as MainViewState.AuthorizedClient
            _uiState.update {
                current.copy(
                    profile = null
                )
            }
            interactor.logout()
            refresh()
        }
    }

    fun getCurrentLang() = interactor.getCurrentLang()

    fun refresh() {
        val currentState = uiState.value
        if (currentState is MainViewState.AuthorizedClient) {
            viewModelScope.launch(coroutineExceptionHandler) {
                if (interactor.isConnectionAvailable()) {
                    interactor.getProfileFromServer()
                    interactor.getEventsFromServer()
                } else {
                    interactor.getProfileFromLocal()
                    interactor.getEventsFromLocal()
                }
            }
        } else {
            viewModelScope.launch(coroutineExceptionHandler){
                if (interactor.isConnectionAvailable()) {
                    interactor.getEventsFromServer()
                } else {
                    interactor.getEventsFromLocal()
                }
            }
        }
        updateState(ProgressState.LOADING)
        initViewModel()
    }

    fun getQuitDialogTitle() = interactor.getQuitDialogTitle()
    fun getQuitDialogDescription() = interactor.getQuitDialogDescription()
    fun getQuitDialogMainBtn() = interactor.getQuitDialogMainBtn()
    fun getQuitDialogSecondBtn() = interactor.getQuitDialogSecondBtn()

     fun isConnectionAvailable() = interactor.isConnectionAvailable()
 }
