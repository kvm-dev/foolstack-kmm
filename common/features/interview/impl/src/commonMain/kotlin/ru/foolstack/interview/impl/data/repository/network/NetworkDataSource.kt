package ru.foolstack.interview.impl.data.repository.network

import ru.foolstack.interview.api.model.MaterialsDomain
import ru.foolstack.interview.impl.mapper.Mapper

class NetworkDataSource(private val api: MaterialsApi, private val mapper: Mapper){

//    suspend fun getMaterials(): MaterialsDomain {
//        return mapper.mapFromResponseMaterialsToDomain(api.getMaterials())
//    }

//    suspend fun getMaterialsByProfession(professionId: Int): MaterialsDomain {
//        return mapper.mapFromResponseMaterialsToDomain(api.getMaterialsByProfession(professionId = professionId))
//    }
}