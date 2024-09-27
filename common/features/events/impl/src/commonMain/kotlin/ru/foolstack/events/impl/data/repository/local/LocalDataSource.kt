package ru.foolstack.events.impl.data.repository.local

import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.events.impl.mapper.Mapper
import ru.foolstack.storage.DatabaseSdk

class LocalDataSource(private val databaseSdk: DatabaseSdk, private val mapper: Mapper) {
    suspend fun getEvents():EventsDomain {
        return EventsDomain(
            events =  mapper.map(databaseSdk.getEvents().events),
            errorMsg = ""
        )
    }
    suspend fun saveEvents(events:EventsDomain){
        databaseSdk.saveEvents(mapper.map(events))
    }
}