package ru.foolstack.splash.api.domain.usecase

import ru.foolstack.splash.api.model.LastVersionDomain

interface CheckUpdateUseCase {
    suspend fun checkUpdate(): LastVersionDomain
}