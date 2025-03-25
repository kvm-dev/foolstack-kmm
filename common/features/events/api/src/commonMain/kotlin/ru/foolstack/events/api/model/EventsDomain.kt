package ru.foolstack.events.api.model

data class EventsDomain(
    val events: List<EventDomain>,
    val errorMsg: String
)

data class EventDomain(
    val eventId: Int,
    val eventName: String,
    val eventDescription: String,
    val eventRefLink: String,
    val eventDateStart: Long,
    val eventCost: Int,
    val eventImageBase64: String,
    val eventSubs: List<EventSubDomain>
)

data class EventSubDomain(
    val subId: Int,
    val subName: String
)