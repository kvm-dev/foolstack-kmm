package ru.foolstack.ui.model

data class EventItem(
    val eventId: Int,
    val eventName: String,
    val eventStartDate: String,
    val eventImageBase64: String,
    val eventTags: List<String>,
    val eventDescription: String,
    val eventCost: Int
)