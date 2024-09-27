package ru.foolstack.study.impl.data.repository.local

import ru.foolstack.storage.DatabaseSdk
import ru.foolstack.study.api.model.StudiesDomain
import ru.foolstack.study.impl.mapper.Mapper

class LocalDataSource(private val databaseSdk: DatabaseSdk, private val mapper: Mapper) {
    suspend fun getStudies(): StudiesDomain = mapper.map(databaseSdk.getStudies())
    suspend fun saveStudies(studies:StudiesDomain) = databaseSdk.saveStudies(mapper.map(studies))
}