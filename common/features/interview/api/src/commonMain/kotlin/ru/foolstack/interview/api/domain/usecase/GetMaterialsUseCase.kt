package ru.foolstack.interview.api.domain.usecase

import ru.foolstack.interview.api.model.MaterialsDomain

interface GetMaterialsUseCase {
    suspend fun getMaterials(fromLocal: Boolean = false): MaterialsDomain
}