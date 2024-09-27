package ru.foolstack.events.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventsResponse(
    @SerialName("events") val events: List<EventResponse> = listOf(),
    @SerialName("errorMsg") val errorMsg: String
)

@Serializable
data class EventResponse(
    @SerialName("eventId") val eventId: Int = 0,
    @SerialName("eventName") val eventName: String = "",
    @SerialName("eventDescription") val eventDescription: String = "",
    @SerialName("eventRefLink") val eventRefLink: String = "",
    @SerialName("eventDateStart") val eventDateStart: Long = 0L,
    @SerialName("eventCost") val eventCost: Int = 0,
    @SerialName("eventImageUrl") val eventImageUrl: String = "",
    @SerialName("eventSubs") val eventSubs: List<Int> = listOf()
)