package ru.foolstack.interview.api.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import ru.foolstack.interview.api.model.MaterialsDomain
import ru.foolstack.utils.model.ResultState

interface GetMaterialsUseCase {

    val materialsState: StateFlow<ResultState<MaterialsDomain>>

    suspend fun getMateialsByProfession(professionId: Int): MaterialsDomain
//    suspend fun getMaterials(fromLocal: Boolean = false): MaterialsDomain
//
//    suspend fun getMaterialsByProfession(professionId: Int, fromLocal: Boolean = false): MaterialsDomain
}