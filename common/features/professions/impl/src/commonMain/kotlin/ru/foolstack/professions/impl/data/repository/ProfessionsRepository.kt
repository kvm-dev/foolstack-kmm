package ru.foolstack.professions.impl.data.repository

import ru.foolstack.professions.api.model.ProfessionsDomain
import ru.foolstack.professions.impl.data.repository.local.LocalDataSource
import ru.foolstack.professions.impl.data.repository.network.NetworkDataSource

class ProfessionsRepository(private val networkDataSource: NetworkDataSource, private val localDataSource: LocalDataSource) {

    suspend fun getProfessionsFromServer():ProfessionsDomain{
        val response = networkDataSource.getProfessions()
        return if(response.errorMsg.isEmpty()){
            localDataSource.saveProfessions(response)
            localDataSource.getProfessions()
        }
        else response
    }

    suspend fun getProfessionsFromLocal():ProfessionsDomain{
        return localDataSource.getProfessions()
    }

    suspend fun getProfessionId() = localDataSource.getProfessionId()

    suspend fun saveProfessionId(professionId: Int) = localDataSource.saveProfessionId(professionId)
}