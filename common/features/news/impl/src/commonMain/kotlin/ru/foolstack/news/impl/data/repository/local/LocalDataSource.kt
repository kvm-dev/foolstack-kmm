package ru.foolstack.news.impl.data.repository.local

import ru.foolstack.news.api.model.NewsDomain
import ru.foolstack.news.impl.mapper.Mapper
import ru.foolstack.storage.DatabaseSdk
import ru.foolstack.storage.prefs.EncryptedPreferences

class LocalDataSource(private val databaseSdk: DatabaseSdk, private val preferences: EncryptedPreferences, private val mapper: Mapper) {
    suspend fun getNews():NewsDomain = mapper.map(databaseSdk.getNews())
    suspend fun saveNews(news:NewsDomain) = databaseSdk.saveNews(mapper.map(news))

    fun getNewsVersion(): Int  = preferences.getNewsVersion()

    fun updateNewsVersion(version: Int) = preferences.updateNewsVersion(version)
}