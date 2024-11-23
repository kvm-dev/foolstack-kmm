package ru.foolstack.professions.impl.data.repository.local

import ru.foolstack.professions.api.model.ProfessionsDomain
import ru.foolstack.professions.impl.mapper.Mapper
import ru.foolstack.storage.DatabaseSdk
import ru.foolstack.storage.prefs.EncryptedPreferences

class LocalDataSource(private val databaseSdk: DatabaseSdk, private val encryptedPreferences: EncryptedPreferences,
                      private val mapper: Mapper) {
    suspend fun getProfessions():ProfessionsDomain{
       return mapper.mapProfessions(databaseSdk.getProfessions().professions)
    }
    suspend fun saveProfessions(professions:ProfessionsDomain) = databaseSdk.saveProfessions(mapper.mapToProfessions(professions))

    suspend fun getProfessionId(): Int{
       return encryptedPreferences.getProfessionId()
    }

    suspend fun saveProfessionId(professionId: Int){
        encryptedPreferences.saveProfessionId(professionId)
    }

}