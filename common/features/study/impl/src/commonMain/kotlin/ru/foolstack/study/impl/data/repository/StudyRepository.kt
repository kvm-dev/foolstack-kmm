package ru.foolstack.study.impl.data.repository

import ru.foolstack.study.api.model.StudiesDomain
import ru.foolstack.study.impl.data.repository.local.LocalDataSource
import ru.foolstack.study.impl.data.repository.network.NetworkDataSource

class StudyRepository(private val networkDataSource: NetworkDataSource, private val localDataSource: LocalDataSource) {
    suspend fun getStudiesFromServer():StudiesDomain{
        val currentVersion = localDataSource.getStudiesVersion()
        val serverVersion = networkDataSource.getVersion().version
        if(currentVersion != serverVersion){

        val result = networkDataSource.getStudies()
        return if(result.errorMsg.isNotEmpty()){
            return result
        }
        else{
            localDataSource.updateStudiesVersion(serverVersion)
            localDataSource.saveStudies(result)
            localDataSource.getStudies()
        }
        }
        else{
            return localDataSource.getStudies()
        }
    }

    suspend fun getStudiesFromLocal():StudiesDomain = localDataSource.getStudies()
}