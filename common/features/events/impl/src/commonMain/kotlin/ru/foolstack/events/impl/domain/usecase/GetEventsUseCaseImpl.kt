package ru.foolstack.events.impl.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.foolstack.events.api.domain.usecase.GetEventsUseCase
import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.events.impl.data.repository.EventsRepository
import ru.foolstack.utils.model.ResultState

class GetEventsUseCaseImpl(private val repository: EventsRepository):GetEventsUseCase {

    private val _events = MutableStateFlow<ResultState<EventsDomain>>(
        ResultState.Idle)
    override val eventsState = _events.asStateFlow()
    override suspend fun getEvents(fromLocal: Boolean): EventsDomain {
        _events.tryEmit(ResultState.Loading)
       return if(fromLocal){
           val cachedEvents = repository.getEventsFromLocal()
           _events.tryEmit(ResultState.Success(cachedEvents))
           cachedEvents
       }
        else{
            val responseEvents = repository.getEventsFromServer()
            _events.tryEmit(ResultState.Success(responseEvents))
            responseEvents
       }
    }
}