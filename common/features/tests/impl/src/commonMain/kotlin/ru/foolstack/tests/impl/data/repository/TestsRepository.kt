package ru.foolstack.tests.impl.data.repository

import ru.foolstack.tests.api.model.TestsDomain
import ru.foolstack.tests.impl.data.repository.local.LocalDataSource
import ru.foolstack.tests.impl.data.repository.network.NetworkDataSource

class TestsRepository(private val localDataSource: LocalDataSource,
                      private val networkDataSource: NetworkDataSource
) {
    suspend fun getTestsFromServer(): TestsDomain {
        val result = networkDataSource.getTests()
        return if (result.errorMsg.isEmpty()) {
            localDataSource.saveTests(result)
            localDataSource.getTests()
        } else {
            result
        }
    }

    suspend fun getTestsFromLocal(): TestsDomain {
        return localDataSource.getTests()
    }
}