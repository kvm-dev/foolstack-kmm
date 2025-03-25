package ru.foolstack.asmode.impl.domain.usecase

import ru.foolstack.asmode.api.domain.usecase.GetAsModeUseCase
import ru.foolstack.asmode.api.model.AsModeDomain
import ru.foolstack.asmode.impl.data.repository.AsModeRepository

class GetAsModeUseCaseImpl(private val repository: AsModeRepository):GetAsModeUseCase {
    override suspend fun isAsModeEnabled(isConnectionAvailable: Boolean): AsModeDomain {
        return repository.isAsEnabled(isConnectionAvailable)
    }
}