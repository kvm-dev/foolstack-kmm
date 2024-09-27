package ru.foolstack.events.impl.mapper

import ru.foolstack.events.api.model.EventDomain
import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.events.impl.model.EventResponse
import ru.foolstack.events.impl.model.EventsResponse
import ru.foolstack.storage.model.Event
import ru.foolstack.storage.model.Events

class Mapper {

     fun map(response: EventResponse, base64Image: String):EventDomain{
        return EventDomain(
            eventId = response.eventId,
            eventName = response.eventName,
            eventDescription = response.eventDescription,
            eventDateStart = response.eventDateStart,
            eventCost = response.eventCost,
            eventImageUrl = response.eventImageUrl,
            eventImageBase64 = base64Image,
            eventRefLink = response.eventRefLink,
            eventSubs =  response.eventSubs
        )
    }

    fun map(eventsList: List<Event>): List<EventDomain>{
        val events = ArrayList<EventDomain>()
        eventsList.forEach { event->
            events.add(map(event))
        }
        return events
    }

    private fun map(event: Event): EventDomain{
        return EventDomain(
            eventId = event.eventId,
            eventName = event.eventName,
            eventDescription = event.eventDescription,
            eventCost = event.eventCost,
            eventRefLink = event.eventRefLink,
            eventDateStart = event.eventDateStart,
            eventImageUrl = event.eventImageUrl,
            eventImageBase64 = event.eventImageBase64,
            eventSubs = event.eventSubs
        )
    }

    fun map(eventsDomain: EventsDomain): Events {
        val events = ArrayList<Event>()
        eventsDomain.events.forEach { event->
            events.add(Event(
                eventId = event.eventId,
                eventName = event.eventName,
                eventDescription = event.eventDescription,
                eventDateStart = event.eventDateStart,
                eventCost = event.eventCost,
                eventRefLink = event.eventRefLink,
                eventImageUrl = event.eventImageUrl,
                eventImageBase64 = event.eventImageBase64,
                eventSubs = event.eventSubs
            ))
        }
        return Events(
            events = events,
            errorMsg = "")
    }
}