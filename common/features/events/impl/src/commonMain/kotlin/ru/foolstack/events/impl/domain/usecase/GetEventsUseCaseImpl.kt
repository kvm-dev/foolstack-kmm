package ru.foolstack.events.impl.domain.usecase

import ru.foolstack.events.api.domain.usecase.GetEventsUseCase
import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.events.impl.data.repository.EventsRepository

class GetEventsUseCaseImpl(private val repository: EventsRepository):GetEventsUseCase {
    override suspend fun getEvents(fromLocal: Boolean): EventsDomain {
       return if(fromLocal){
           repository.getEventsFromLocal()
       }
        else{
            repository.getEventsFromServer()
       }
    }
}