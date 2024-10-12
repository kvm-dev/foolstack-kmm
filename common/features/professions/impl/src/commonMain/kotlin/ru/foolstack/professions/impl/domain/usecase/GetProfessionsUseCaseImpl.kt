package ru.foolstack.professions.impl.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.foolstack.professions.api.domain.usecase.GetProfessionsUseCase
import ru.foolstack.professions.api.model.ProfessionsDomain
import ru.foolstack.professions.impl.data.repository.ProfessionsRepository
import ru.foolstack.utils.model.ResultState

class GetProfessionsUseCaseImpl(private val repository: ProfessionsRepository):GetProfessionsUseCase {

    private val _professions = MutableStateFlow<ResultState<ProfessionsDomain>>(
        ResultState.Idle)
    override val professionsState = _professions.asStateFlow()
    override suspend fun getProfessions(fromLocal: Boolean): ProfessionsDomain {
        _professions.tryEmit(ResultState.Loading)
        return if(fromLocal){
            val cachedProfessions = repository.getProfessionsFromLocal()
            _professions.tryEmit(ResultState.Success(cachedProfessions))
            cachedProfessions
        } else {
            val responseProfessions = repository.getProfessionsFromServer()
            _professions.tryEmit(ResultState.Success(responseProfessions))
            responseProfessions
        }
    }
}