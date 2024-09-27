package ru.foolstack.study.api.domain.usecase

import ru.foolstack.study.api.model.StudiesDomain

interface GetStudiesUseCase {

    suspend fun getStudies(fromLocal: Boolean = false):StudiesDomain
}