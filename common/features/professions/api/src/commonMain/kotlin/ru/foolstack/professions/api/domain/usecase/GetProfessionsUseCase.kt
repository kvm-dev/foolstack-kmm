package ru.foolstack.professions.api.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import ru.foolstack.professions.api.model.ProfessionsDomain
import ru.foolstack.utils.model.ResultState

interface GetProfessionsUseCase {

    val professionsState: StateFlow<ResultState<ProfessionsDomain>>
    suspend fun getProfessions(fromLocal: Boolean = false): ProfessionsDomain

    suspend fun getProfessionId():Int

    suspend fun saveProfessionId(professionId: Int)
}