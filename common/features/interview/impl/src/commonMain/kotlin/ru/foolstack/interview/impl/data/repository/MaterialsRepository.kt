package ru.foolstack.interview.impl.data.repository

import ru.foolstack.interview.api.model.MaterialsDomain
import ru.foolstack.interview.impl.data.repository.local.LocalDataSource
import ru.foolstack.interview.impl.data.repository.network.NetworkDataSource

class MaterialsRepository(private val localDataSource: LocalDataSource,
                          private val networkDataSource: NetworkDataSource) {
    suspend fun getMaterialsFromServer(): MaterialsDomain {
        val result = networkDataSource.getMaterials()
        return if (result.errorMsg.isEmpty()) {
            localDataSource.saveMaterials(result)
            localDataSource.getMaterials()
        } else {
                result
            }
        }

    suspend fun getMaterialsFromLocal(): MaterialsDomain {
        return localDataSource.getMaterials()
    }
}