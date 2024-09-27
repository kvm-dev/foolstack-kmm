package ru.foolstack.tests.impl.data.repository.local

import ru.foolstack.storage.DatabaseSdk
import ru.foolstack.tests.api.model.TestsDomain
import ru.foolstack.tests.impl.mapper.Mapper

class LocalDataSource(private val databaseSdk: DatabaseSdk, private val mapper: Mapper) {
    suspend fun getTests():TestsDomain {
        return mapper.mapTestsDomain(databaseSdk.getTests())
    }

    suspend fun saveTests(tests: TestsDomain){
        databaseSdk.saveTests(tests = mapper.map(tests))
    }
}