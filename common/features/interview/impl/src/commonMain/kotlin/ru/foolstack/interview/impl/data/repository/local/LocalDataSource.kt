package ru.foolstack.interview.impl.data.repository.local

import ru.foolstack.interview.api.model.MaterialsDomain
import ru.foolstack.interview.impl.data.repository.local.professions.AndroidSourceData
import ru.foolstack.interview.impl.mapper.Mapper
import ru.foolstack.storage.DatabaseSdk

class LocalDataSource(private val databaseSdk: DatabaseSdk, private val mapper: Mapper) {
    suspend fun getMaterials():MaterialsDomain {
        return mapper.mapMaterialsDomain(databaseSdk.getMaterials())
    }

    suspend fun saveMaterials(materials: MaterialsDomain){
        databaseSdk.saveMaterials(materials = mapper.mapMaterials(materials))
    }

    suspend fun getAndroidMaterials() = AndroidSourceData().getData()
}