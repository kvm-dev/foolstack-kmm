package ru.foolstack.study.api.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import ru.foolstack.study.api.model.StudiesDomain
import ru.foolstack.utils.model.ResultState

interface GetStudiesUseCase {

    val studiesState: StateFlow<ResultState<StudiesDomain>>

    suspend fun getStudies(fromLocal: Boolean = false):StudiesDomain
}