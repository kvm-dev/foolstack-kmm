package ru.foolstack.study.impl.data.repository.network

import ru.foolstack.network.utils.getBase64Bitmap
import ru.foolstack.study.api.model.StudiesDomain
import ru.foolstack.study.api.model.StudyDomain
import ru.foolstack.study.impl.mapper.Mapper

class NetworkDataSource(private val api: StudyApi, private val mapper: Mapper){

    suspend fun getStudies(): StudiesDomain {
        val response = api.getStudies()
        val studiesList = ArrayList<StudyDomain>()
        response.studies.forEach { study->
            var studyImageBase64 = ""
            if(study.studyImageUrl.isNotEmpty()){
                studyImageBase64 = getBase64Bitmap(study.studyImageUrl)
            }
            studiesList.add(mapper.map(study, studyImageBase64))
        }
        return StudiesDomain(
            studies = studiesList,
            prText = response.prText,
            errorMsg = response.errorMsg)
    }
}