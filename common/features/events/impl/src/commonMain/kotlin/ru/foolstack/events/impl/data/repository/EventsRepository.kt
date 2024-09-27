package ru.foolstack.events.impl.data.repository

import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.events.impl.data.repository.local.LocalDataSource
import ru.foolstack.events.impl.data.repository.network.NetworkDataSource

class EventsRepository(private val networkDataSource: NetworkDataSource, private val localDataSource: LocalDataSource) {

    suspend fun getEventsFromServer():EventsDomain{
        val response = networkDataSource.getEvents()
        return if(response.errorMsg.isNotEmpty()){
            response
        }
        else{
            localDataSource.saveEvents(response)
            localDataSource.getEvents()
        }
    }

    suspend fun getEventsFromLocal():EventsDomain = localDataSource.getEvents()
}