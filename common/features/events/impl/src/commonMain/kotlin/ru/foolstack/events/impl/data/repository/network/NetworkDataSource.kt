package ru.foolstack.events.impl.data.repository.network

import ru.foolstack.events.api.model.EventDomain
import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.events.impl.mapper.Mapper
import ru.foolstack.network.utils.getBase64Bitmap

class NetworkDataSource(private val api: EventsApi, private val mapper: Mapper){

    suspend fun getEvents(): EventsDomain {
        val response = api.getEvents()
        val eventsList = ArrayList<EventDomain>()
        response.events.forEach { event->
            var eventImageBase64 = ""
            if(event.eventImageUrl.isNotEmpty()){
                eventImageBase64 = getBase64Bitmap(event.eventImageUrl)
            }
            eventsList.add(mapper.map(event, eventImageBase64))
        }
        return EventsDomain(
            events = eventsList,
            errorMsg = response.errorMsg
        )
    }
}