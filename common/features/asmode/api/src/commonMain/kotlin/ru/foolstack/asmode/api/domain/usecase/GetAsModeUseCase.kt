package ru.foolstack.asmode.api.domain.usecase

import ru.foolstack.asmode.api.model.AsModeDomain

interface GetAsModeUseCase {
    suspend fun isAsModeEnabled(isConnectionAvailable: Boolean): AsModeDomain
}