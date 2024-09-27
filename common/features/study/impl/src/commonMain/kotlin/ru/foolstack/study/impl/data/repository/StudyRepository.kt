package ru.foolstack.study.impl.data.repository

import ru.foolstack.study.api.model.StudiesDomain
import ru.foolstack.study.impl.data.repository.local.LocalDataSource
import ru.foolstack.study.impl.data.repository.network.NetworkDataSource

class StudyRepository(private val networkDataSource: NetworkDataSource, private val localDataSource: LocalDataSource) {
    suspend fun getStudiesFromServer():StudiesDomain{
        val result = networkDataSource.getStudies()
        return if(result.errorMsg.isNotEmpty()){
            return result
        }
        else{
            localDataSource.saveStudies(result)
            localDataSource.getStudies()
        }
    }

    suspend fun getStudiesFromLocal():StudiesDomain = localDataSource.getStudies()
}