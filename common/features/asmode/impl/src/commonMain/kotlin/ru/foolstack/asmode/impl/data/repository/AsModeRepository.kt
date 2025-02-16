package ru.foolstack.asmode.impl.data.repository

import ru.foolstack.asmode.api.model.AsModeDomain
import ru.foolstack.asmode.impl.data.repository.network.NetworkDataSource

class AsModeRepository(private val networkDataSource: NetworkDataSource) {
    suspend fun getAsModeStatus(): AsModeDomain {
       return  networkDataSource.getAsModeStatus()
    }
}