package ru.foolstack.study.impl.data.repository.network

import ru.foolstack.study.api.model.StudiesDomain
import ru.foolstack.study.api.model.StudyDomain
import ru.foolstack.study.impl.mapper.Mapper
import ru.foolstack.study.impl.model.StudiesVersionResponse

class NetworkDataSource(private val api: StudyApi, private val mapper: Mapper){

    suspend fun getStudies(): StudiesDomain {
        val response = api.getStudies()
        val studiesList = ArrayList<StudyDomain>()
        response.studies.forEach { study->
            studiesList.add(mapper.map(study))
        }
        return StudiesDomain(
            studies = studiesList,
            prText = response.prText,
            errorMsg = response.errorMsg)
    }

    suspend fun getVersion():StudiesVersionResponse{
        return api.getVersion()
    }
}