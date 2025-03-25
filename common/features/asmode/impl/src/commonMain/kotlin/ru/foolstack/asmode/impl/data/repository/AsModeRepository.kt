package ru.foolstack.asmode.impl.data.repository

import ru.foolstack.asmode.api.model.AsModeDomain
import ru.foolstack.asmode.impl.data.repository.local.LocalDataSource
import ru.foolstack.asmode.impl.data.repository.network.NetworkDataSource

class AsModeRepository(private val networkDataSource: NetworkDataSource, private val localDataSource: LocalDataSource) {
    suspend fun isAsEnabled(isConnectionAvailable: Boolean): AsModeDomain {
        return if(!isConnectionAvailable){
            AsModeDomain(localDataSource.isAsEnabled())
        } else{
            if(localDataSource.isAsEnabled()){
                return AsModeDomain(localDataSource.isAsEnabled())
            }
            else{
                networkDataSource.getAsModeStatus()
            }
        }
    }
}