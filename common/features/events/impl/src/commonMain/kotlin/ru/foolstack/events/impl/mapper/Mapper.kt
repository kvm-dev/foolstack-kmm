package ru.foolstack.events.impl.mapper

import ru.foolstack.events.api.model.EventDomain
import ru.foolstack.events.api.model.EventSubDomain
import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.events.impl.model.EventResponse
import ru.foolstack.storage.model.Event
import ru.foolstack.storage.model.Events
import ru.foolstack.storage.model.EventSub
import ru.foolstack.ui.model.EventItem
import ru.foolstack.ui.model.Chip
import ru.foolstack.ui.utils.timestampToDateString

class Mapper {

     fun map(response: EventResponse):EventDomain{
         val subs = ArrayList<EventSubDomain>()
         response.eventSubs.forEach { sub->
             subs.add(EventSubDomain(
                 subId = sub.subId,
                 subName = sub.subName
             ))
         }
        return EventDomain(
            eventId = response.eventId,
            eventName = response.eventName,
            eventDescription = response.eventDescription,
            eventDateStart = response.eventDateStart,
            eventCost = response.eventCost,
            eventImageBase64 = response.eventImage,
            eventRefLink = response.eventRefLink,
            eventSubs =  subs
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
        val subs = ArrayList<EventSubDomain>()
        event.eventSubs.forEach { sub->
            subs.add(EventSubDomain(
                subId = sub.subId,
                subName = sub.subName
            ))
        }
        return EventDomain(
            eventId = event.eventId,
            eventName = event.eventName,
            eventDescription = event.eventDescription,
            eventCost = event.eventCost,
            eventRefLink = event.eventRefLink,
            eventDateStart = event.eventDateStart,
            eventImageBase64 = event.eventImageBase64,
            eventSubs = subs
        )
    }

    fun map(eventsDomain: EventsDomain): Events {
        val events = ArrayList<Event>()
        eventsDomain.events.forEach { event->
            val subs = ArrayList<EventSub>()
            event.eventSubs.forEach { sub->
                subs.add(EventSub(
                    subId = sub.subId,
                    subName = sub.subName
                ))
            }
            events.add(Event(
                eventId = event.eventId,
                eventName = event.eventName,
                eventDescription = event.eventDescription,
                eventDateStart = event.eventDateStart,
                eventCost = event.eventCost,
                eventRefLink = event.eventRefLink,
                eventImageBase64 = event.eventImageBase64,
                eventSubs = subs
            ))
        }
        return Events(
            events = events,
            errorMsg = "")
    }

    private fun mapToEventChip(sub: EventSubDomain): Chip {
        return Chip(
            id = sub.subId,
            name = sub.subName
        )
    }

    fun mapToChips(eventList: List<EventDomain>): List<Chip>{
        val list = HashSet<Chip>()
        eventList.forEach { event->
            event.eventSubs.forEach {sub->
                list.add(mapToEventChip(sub))
            }
        }
        return list.toList()
    }

    fun mapToEventItems(eventsDomain: EventsDomain?): List<EventItem>{
        val eventList = ArrayList<EventItem>()
        eventsDomain?.events?.forEach {event->
            val tags = ArrayList<String>()
            event.eventSubs.forEach {
                tags.add(it.subName)
            }
            eventList.add(
                EventItem(
                eventId = event.eventId,
                eventName = event.eventName,
                eventStartDate = event.eventDateStart.timestampToDateString(),
                eventImageBase64 = event.eventImageBase64,
                eventTags = tags,
                eventDescription = event.eventDescription,
                eventCost = event.eventCost
            )
            )
        }
        return eventList
    }
}