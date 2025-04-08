package ru.foolstack.events.impl.data.repository.network

import ru.foolstack.events.api.model.EventDomain
import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.events.impl.mapper.Mapper
import ru.foolstack.events.impl.model.EventsVersionResponse

class NetworkDataSource(private val api: EventsApi, private val mapper: Mapper){

    suspend fun getEvents(): EventsDomain {
        val response = api.getEvents()
        val eventsList = ArrayList<EventDomain>()
        response.events.forEach { event->
            eventsList.add(mapper.map(event))
        }
        return EventsDomain(
            events = eventsList,
            errorMsg = response.errorMsg
        )
    }

    suspend fun getVersion():EventsVersionResponse{
        return api.getVersion()
    }
}