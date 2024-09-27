package ru.foolstack.tests.impl.data.repository.network

import ru.foolstack.tests.api.model.TestsDomain
import ru.foolstack.tests.impl.mapper.Mapper

class NetworkDataSource(private val api: TestsApi, private val mapper: Mapper){

    suspend fun getTests(): TestsDomain {
        return mapper.mapTestsDomainFromResponse(api.getTests())
    }
}