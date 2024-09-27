package ru.foolstack.main.impl.mapper

import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.ui.model.EventItem

class Mapper {
    fun map(eventsDomain: EventsDomain): List<EventItem>{
        val eventList = ArrayList<EventItem>()
        eventsDomain.events.forEach {event->
            eventList.add(EventItem(
                eventId = event.eventId,
                eventName = event.eventName,
                eventStartDate = event.eventDateStart.toString(),
                eventImageBase64 = event.eventImageBase64,
                eventTags = listOf("free", "pay"),
                eventDescription = event.eventDescription
            ))
        }
        return eventList
    }
}