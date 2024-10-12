package ru.foolstack.study.impl.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.foolstack.study.api.domain.usecase.GetStudiesUseCase
import ru.foolstack.study.api.model.StudiesDomain
import ru.foolstack.study.impl.data.repository.StudyRepository
import ru.foolstack.utils.model.ResultState

class GetStudiesUseCaseImpl(private val repository: StudyRepository): GetStudiesUseCase {

    private val _studies = MutableStateFlow<ResultState<StudiesDomain>>(
        ResultState.Idle)
    override val studiesState = _studies.asStateFlow()
    override suspend fun getStudies(fromLocal: Boolean): StudiesDomain {
        _studies.tryEmit(ResultState.Loading)
        return if(fromLocal){
            val cachedStudies = repository.getStudiesFromLocal()
            _studies.tryEmit(ResultState.Success(cachedStudies))
            cachedStudies
        }
        else{
            val responseStudies = repository.getStudiesFromServer()
            _studies.tryEmit(ResultState.Success(responseStudies))
            responseStudies
        }
    }
}