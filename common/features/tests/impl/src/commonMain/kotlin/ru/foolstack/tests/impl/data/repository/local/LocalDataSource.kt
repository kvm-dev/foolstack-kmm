package ru.foolstack.tests.impl.data.repository.local

import ru.foolstack.storage.DatabaseSdk
import ru.foolstack.storage.model.PassedTest
import ru.foolstack.tests.api.model.PassedTestDomain
import ru.foolstack.tests.api.model.PassedTestsDomain
import ru.foolstack.tests.api.model.TestsDomain
import ru.foolstack.tests.impl.mapper.Mapper

class LocalDataSource(private val databaseSdk: DatabaseSdk, private val mapper: Mapper) {
    suspend fun getTests():TestsDomain {
        return mapper.mapTestsDomain(databaseSdk.getTests())
    }

    suspend fun getPassedTests():PassedTestsDomain {
        return mapper.mapPassedTestsDomainFromLocal(databaseSdk.getPassedTests())
    }

    suspend fun savePassedTests(passedTests: PassedTestsDomain){

        databaseSdk.savePassedTests(passedTests = mapper.mapPassedTestsDomainToPassedTests(passedTests))
    }

    suspend fun savePassedTest(passedTest: PassedTestDomain){
        databaseSdk.savePassedTest(passedTest = mapper.mapPassedTestDomainToPassedTest(passedTest))
    }

    suspend fun saveTests(tests: TestsDomain){
        databaseSdk.saveTests(tests = mapper.map(tests))
    }
}