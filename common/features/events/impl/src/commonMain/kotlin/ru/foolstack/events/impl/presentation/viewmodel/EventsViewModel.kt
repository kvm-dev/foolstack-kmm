package ru.foolstack.events.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.foolstack.events.impl.domain.interactor.EventsInteractor
import ru.foolstack.events.impl.presentation.ui.EventsViewState
import ru.foolstack.model.ProgressState
import ru.foolstack.utils.model.ResultState
import ru.foolstack.viewmodel.BaseViewModel

class EventsViewModel(private val interactor: EventsInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<EventsViewState>(
        EventsViewState.LoadingState(lang = interactor.getCurrentLang())
    )

    val uiState: StateFlow<EventsViewState> = _uiState.asStateFlow()
    var asMode = false

    fun initViewModel() = with(viewModelScope + coroutineExceptionHandler) {
        if(progressState.value == ProgressState.LOADING){
            launch {
                asMode = interactor.isAsModeActive()
                if(interactor.eventsState.value !is ResultState.Success || interactor.eventsState.value !is ResultState.Loading ){
                    if(interactor.isConnectionAvailable()){
                        interactor.getEventsFromServer()
                    }
                    else{
                        interactor.getEventsFromLocal()
                    }
                }
            }
            launch{
                interactor.eventsState.collect{ resultState->
                    _uiState.update { interactor.checkState(resultState) }
                    updateState(ProgressState.COMPLETED)
                }
            }
        }
    }

    fun refresh() = with(viewModelScope + coroutineExceptionHandler){
        val lang = interactor.getCurrentLang()
        _uiState.update { EventsViewState.LoadingState(lang = lang) }
        launch {
            if(interactor.isConnectionAvailable()){
                interactor.getEventsFromServer()
            }
            else{
                interactor.getEventsFromLocal()
            }
            updateState(ProgressState.LOADING)
            initViewModel()
        }
    }

    fun updateFilters(subName: String){
        if(subName.isNotEmpty()){
            val current = uiState.value as EventsViewState.SuccessState
            val selectedFilters = HashSet<String>()
            current.selectedFilters.forEach { sub->
                selectedFilters.add(sub)
            }
            if(selectedFilters.contains(subName)){
                selectedFilters.remove(subName)
            }
            else{
                selectedFilters.add(subName)
            }

            _uiState.update { current.copy(selectedFilters = selectedFilters.toList()) }
        }
    }

    fun navigateToEvent(navController: NavController, eventId: Int, eventDestination: String){
        val route = "$eventDestination/{eventId}"
        navController.navigate(
            route.replace(
                oldValue = "{eventId}",
                newValue = eventId.toString()
            )
        )
    }

    fun isConnectionAvailable() = interactor.isConnectionAvailable()
    fun getCurrentLang() = interactor.getCurrentLang()
}