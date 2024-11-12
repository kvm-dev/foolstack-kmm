package ru.foolstack.events.impl.domain.interactor

import ru.foolstack.events.api.domain.usecase.GetEventsUseCase
import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.events.impl.presentation.ui.EventCardViewState
import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.utils.BrowserUtils
import ru.foolstack.utils.model.ResultState

class EventCardInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    getEventsUseCase: GetEventsUseCase,
    private val browserUtils: BrowserUtils
){
    val eventsState = getEventsUseCase.eventsState

    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    private fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()

    fun checkState(state: ResultState<EventsDomain>, eventId: Int): EventCardViewState {
        val lang = getCurrentLang()
        return when(state){
            is ResultState.Loading->{
                EventCardViewState.LoadingState(lang = lang)
            }

            is ResultState.Idle->{
                EventCardViewState.LoadingState(lang = lang)
            }

            is ResultState.Success->{
                if(state.data?.errorMsg?.isNotEmpty() == true){
                    EventCardViewState.ErrorState(lang = lang)
                } else{
                    EventCardViewState.SuccessState(isHaveConnection = isConnectionAvailable(), event = state.data?.events?.find { it.eventId == eventId }, lang = lang)
                }
            }
        }
    }

    fun openInBrowser(url: String){
        browserUtils.openInBrowser(url)
    }
}