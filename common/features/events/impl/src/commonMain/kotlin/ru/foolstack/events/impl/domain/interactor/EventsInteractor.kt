package ru.foolstack.events.impl.domain.interactor

import ru.foolstack.events.api.domain.usecase.GetEventsUseCase
import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.events.impl.presentation.ui.EventsViewState
import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.utils.model.ResultState

class EventsInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getEventsUseCase: GetEventsUseCase
){
    val eventsState = getEventsUseCase.eventsState

    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()

    suspend fun getEventsFromServer() = getEventsUseCase.getEvents()
    suspend fun getEventsFromLocal() = getEventsUseCase.getEvents(fromLocal = true)

    fun checkState(state: ResultState<EventsDomain>):EventsViewState{
        val lang = getCurrentLang()
        when(state){
            is ResultState.Loading->{
                return EventsViewState.LoadingState(lang = lang)
            }
            is ResultState.Idle->{
                return EventsViewState.LoadingState(lang = lang)
            }
            is ResultState.Success->{
                return if(state.data?.errorMsg?.isNotEmpty() == true){
                    EventsViewState.ErrorState(lang = lang)
                } else{
                    EventsViewState.SuccessState(isHaveConnection = isConnectionAvailable(), events = state.data, lang = lang)
                }
            }

        }
    }
}