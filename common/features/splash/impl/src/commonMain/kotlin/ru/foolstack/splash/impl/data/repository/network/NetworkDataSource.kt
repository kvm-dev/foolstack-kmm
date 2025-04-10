package ru.foolstack.splash.impl.data.repository.network

import ru.foolstack.splash.api.model.LastVersionDomain
import ru.foolstack.splash.impl.mapper.Mapper

class NetworkDataSource(private val api: SplashApi, private val mapper: Mapper) {

    suspend fun checkUpdate(): LastVersionDomain {
        return mapper.map(api.checkUpdate())
    }
}