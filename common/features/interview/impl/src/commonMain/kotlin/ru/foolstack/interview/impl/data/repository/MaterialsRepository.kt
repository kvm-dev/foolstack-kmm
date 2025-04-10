package ru.foolstack.interview.impl.data.repository

import ru.foolstack.interview.api.model.MaterialsDomain
import ru.foolstack.interview.impl.data.repository.local.LocalDataSource
import ru.foolstack.interview.impl.data.repository.network.NetworkDataSource
import ru.foolstack.interview.impl.mapper.Mapper

class MaterialsRepository(private val localDataSource: LocalDataSource,
                          private val mapper: Mapper) {

    suspend fun getMaterialsByProfession(professionId: Int): MaterialsDomain{
        return when(professionId){
            1 -> {
                mapper.mapFromResponseMaterialsToDomain(localDataSource.getAndroidMaterials())
            }
            2-> {
                mapper.mapFromResponseMaterialsToDomain(localDataSource.getAndroidMaterials())
            }
            3-> {
                mapper.mapFromResponseMaterialsToDomain(localDataSource.getAndroidMaterials())
            }
            10-> {
                mapper.mapFromResponseMaterialsToDomain(localDataSource.getAndroidMaterials())
            }
            else-> {
                mapper.mapFromResponseMaterialsToDomain(localDataSource.getAndroidMaterials())
            }
        }
    }


//    suspend fun getMaterialsFromServer(): MaterialsDomain {
//        val result = networkDataSource.getMaterials()
//        return if (result.errorMsg.isEmpty()) {
//            localDataSource.saveMaterials(result)
//            localDataSource.getMaterials()
//        } else {
//                result
//            }
//        }

//    suspend fun getMaterialsByProfessionFromServer(professionId: Int): MaterialsDomain {
//        val result = networkDataSource.getMaterialsByProfession(professionId = professionId)
//        return if (result.errorMsg.isEmpty()) {
//            localDataSource.saveMaterials(result)
//            localDataSource.getMaterials()
//        } else {
//            result
//        }
//    }

//    suspend fun getMaterialsFromLocal(): MaterialsDomain {
//        return localDataSource.getMaterials()
//    }
}