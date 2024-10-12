package ru.foolstack.interview.api.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import ru.foolstack.interview.api.model.MaterialsDomain
import ru.foolstack.utils.model.ResultState

interface GetMaterialsUseCase {

    val materialsState: StateFlow<ResultState<MaterialsDomain>>
    suspend fun getMaterials(fromLocal: Boolean = false): MaterialsDomain
}