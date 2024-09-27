package ru.foolstack.events.api.domain.usecase

import ru.foolstack.events.api.model.EventsDomain

interface GetEventsUseCase {
    suspend fun getEvents(fromLocal: Boolean = false):EventsDomain
}