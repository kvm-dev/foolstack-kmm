package ru.foolstack.main.impl.mapper

import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.profile.api.model.AchievementDomain
import ru.foolstack.ui.model.AchievementItem
import ru.foolstack.ui.model.EventItem
import ru.foolstack.ui.utils.timestampToDateString

class Mapper {
    fun map(eventsDomain: EventsDomain?): List<EventItem>{
        val eventList = ArrayList<EventItem>()
        eventsDomain?.events?.forEach {event->
            val subs = ArrayList<String>()
            event.eventSubs.forEach {eventSub->
                subs.add(eventSub.subName)
            }
            eventList.add(EventItem(
                eventId = event.eventId,
                eventName = event.eventName,
                eventStartDate = event.eventDateStart.timestampToDateString(),
                eventImageBase64 = event.eventImageBase64,
                eventTags = subs,
                eventDescription = event.eventDescription,
                eventCost = event.eventCost
            ))
        }
        return eventList
    }

    fun map(achievementList: List<AchievementDomain>): List<AchievementItem>{
        val achievementsList = ArrayList<AchievementItem>()
        achievementList.forEach {achieve->
            achievementsList.add(AchievementItem(
                achievementId = achieve.achievementId,
                achievementName = achieve.achievementName,
                achievementDescription = achieve.achievementDescription,
                achievementLevel = achieve.achievementLevel
            ))
        }
        return achievementsList
    }
}