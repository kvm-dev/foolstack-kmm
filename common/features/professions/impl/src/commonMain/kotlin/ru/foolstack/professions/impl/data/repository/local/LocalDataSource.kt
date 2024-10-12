package ru.foolstack.professions.impl.data.repository.local

import ru.foolstack.professions.api.model.ProfessionsDomain
import ru.foolstack.professions.impl.mapper.Mapper
import ru.foolstack.storage.DatabaseSdk

class LocalDataSource(private val databaseSdk: DatabaseSdk, private val mapper: Mapper) {
    suspend fun getProfessions():ProfessionsDomain{
       return mapper.mapProfessions(databaseSdk.getProfessions().professions)
    }
    suspend fun saveProfessions(professions:ProfessionsDomain) = databaseSdk.saveProfessions(mapper.mapToProfessions(professions))

}