package ru.foolstack.study.impl.domain.usecase

import ru.foolstack.study.api.domain.usecase.GetStudiesUseCase
import ru.foolstack.study.api.model.StudiesDomain
import ru.foolstack.study.impl.data.repository.StudyRepository

class GetStudiesUseCaseImpl(private val repository: StudyRepository): GetStudiesUseCase {
    override suspend fun getStudies(fromLocal: Boolean): StudiesDomain {
        return if(fromLocal){
            repository.getStudiesFromLocal()
        }
        else{
            repository.getStudiesFromServer()
        }
    }
}