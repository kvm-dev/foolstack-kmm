package ru.foolstack.asmode.impl.data.repository.network

import ru.foolstack.asmode.api.model.AsModeDomain
import ru.foolstack.asmode.impl.mapper.Mapper

class NetworkDataSource(private val api: AsModeApi, private val mapper: Mapper){

    suspend fun getAsModeStatus(): AsModeDomain  = mapper.mapToAsModeDomain(api.getAsMode())

}