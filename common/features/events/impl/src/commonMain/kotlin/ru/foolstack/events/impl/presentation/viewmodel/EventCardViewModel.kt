package ru.foolstack.events.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.foolstack.events.impl.domain.interactor.EventCardInteractor
import ru.foolstack.events.impl.presentation.ui.EventCardViewState
import ru.foolstack.model.ProgressState
import ru.foolstack.utils.model.ResultState
import ru.foolstack.viewmodel.BaseViewModel

class EventCardViewModel(private val interactor: EventCardInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<EventCardViewState>(
        EventCardViewState.Idle(lang = interactor.getCurrentLang()))


    val uiState: StateFlow<EventCardViewState> = _uiState.asStateFlow()

    fun initViewModel(eventId: Int) {
        if(progressState.value == ProgressState.LOADING){
            viewModelScope.launch {
                interactor.eventsState.collect{ resultState->
                    if(resultState is ResultState.Success){
                        _uiState.update { EventCardViewState.SuccessState(
                            isHaveConnection = interactor.isConnectionAvailable(),
                            lang = interactor.getCurrentLang(),
                            event = resultState.data?.events?.find { it.eventId == eventId }) }
                        updateState(ProgressState.COMPLETED)
                    }
                }
            }
        }
    }

    fun onClickJoinEvent(url: String){
        interactor.openInBrowser(url)
    }
}