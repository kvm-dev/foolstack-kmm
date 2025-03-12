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

    fun initViewModel() = with(viewModelScope + coroutineExceptionHandler) {
        if (progressState.value == ProgressState.LOADING) {
            launch {
                asMode = interactor.isAsModeActive()
                if(interactor.eventsState.value !is ResultState.Success){
                    if (interactor.isConnectionAvailable()) {
                        interactor.getEventsFromServer()
                    } else {
                        interactor.getEventsFromLocal()
                    }
                }
                interactor.eventsState.collect { eventsState ->
                    interactor.profileState.collect { profileState ->
                        val state = interactor.checkState(eventsState = eventsState, profileState = profileState)
                        if(state !is MainViewState.Loading){
                            _uiState.update {
                                state
                            }
                        }
                        updateState(ProgressState.COMPLETED)
                    }
                }
            }
            //getAdditionalData
            launch(Dispatchers.IO){
                interactor.getAdditionalData()
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

    fun getSettingsGuestDialogTitle() = interactor.getOnlyClientsDialogSettingsTitle()

    fun getSettingsGuestDialogText() = interactor.getOnlyClientsDialogSettingsText()

    fun getDialogOkBtn() = interactor.getDialogsOkBtn()

    fun getGuestNotificationDialogTitle() = interactor.getGuestNotificationDialogTitle()

    fun getGuestNotificationDialogText() = interactor.getGuestNotificationDialogText()

    fun getGuestNotificationDialogActionBtn() = interactor.getGuestNotificationDialogActionBtn()

    fun getGuestNotificationDialogSecondBtn() = interactor.getGuestNotificationDialogSecondBtn()

    fun refresh() = with(viewModelScope + coroutineExceptionHandler) {
        val currentState = uiState.value
        if (currentState is MainViewState.AuthorizedClient) {
            launch {
                if (interactor.isConnectionAvailable()) {
                    interactor.getProfileFromServer()
                    interactor.getEventsFromServer()
                } else {
                    interactor.getProfileFromLocal()
                    interactor.getEventsFromLocal()
                }
            }
            launch(Dispatchers.IO){
                if (interactor.isConnectionAvailable()) {
                    interactor.getNewsFromServer()
                    interactor.getBooksFromServer()
                    interactor.getStudiesFromServer()
                } else {
                    interactor.getNewsFromLocal()
                    interactor.getBooksFromLocal()
                    interactor.getStudiesFromLocal()
                }
            }
        } else {
            launch {
                if (interactor.isConnectionAvailable()) {
                    interactor.getEventsFromServer()
                } else {
                    interactor.getEventsFromLocal()
                }
            }
            launch(Dispatchers.IO){
                if (interactor.isConnectionAvailable()) {
                    interactor.getNewsFromServer()
                    interactor.getBooksFromServer()
                    interactor.getStudiesFromServer()
                } else {
                    interactor.getNewsFromLocal()
                    interactor.getBooksFromLocal()
                    interactor.getStudiesFromLocal()
                }
            }
        }
        updateState(ProgressState.LOADING)
            initViewModel()
    }

     fun isConnectionAvailable() = interactor.isConnectionAvailable()
 }
