package ru.foolstack.events.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.foolstack.events.impl.domain.interactor.EventsInteractor
import ru.foolstack.events.impl.presentation.ui.EventsViewState
import ru.foolstack.model.ProgressState
import ru.foolstack.viewmodel.BaseViewModel

class EventsViewModel(private val interactor: EventsInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<EventsViewState>(
        EventsViewState.LoadingState(lang = interactor.getCurrentLang())
    )

    val uiState: StateFlow<EventsViewState> = _uiState.asStateFlow()

    fun initViewModel() = with(viewModelScope) {
        if(progressState.value == ProgressState.LOADING){
            viewModelScope.launch {
                interactor.eventsState.collect{ resultState->
                    _uiState.update { interactor.checkState(resultState) }
                    updateState(ProgressState.COMPLETED)
                }
            }
        }
    }

    fun refresh() = with(viewModelScope){
        val lang = interactor.getCurrentLang()
        _uiState.update { EventsViewState.LoadingState(lang = lang) }
        viewModelScope.launch {
            interactor.getEventsFromServer()
            updateState(ProgressState.LOADING)
            initViewModel()
        }
    }
}