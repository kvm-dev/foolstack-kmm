package ru.foolstack.events.api.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.utils.model.ResultState

interface GetEventsUseCase {

    val eventsState: StateFlow<ResultState<EventsDomain>>
    suspend fun getEvents(fromLocal: Boolean = false):EventsDomain
}