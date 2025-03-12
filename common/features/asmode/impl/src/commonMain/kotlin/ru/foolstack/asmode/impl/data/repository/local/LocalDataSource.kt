package ru.foolstack.asmode.impl.data.repository.local

import ru.foolstack.storage.DatabaseSdk
class LocalDataSource(private val databaseSdk: DatabaseSdk) {
    suspend fun isAsEnabled(): Boolean {
        return databaseSdk.getProfile().userEmail == "test@foolstack.ru"

    }
}