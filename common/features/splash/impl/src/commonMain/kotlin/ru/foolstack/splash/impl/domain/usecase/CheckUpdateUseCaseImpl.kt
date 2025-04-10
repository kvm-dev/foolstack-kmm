package ru.foolstack.splash.impl.domain.usecase

import ru.foolstack.splash.api.domain.usecase.CheckUpdateUseCase
import ru.foolstack.splash.api.model.LastVersionDomain
import ru.foolstack.splash.impl.data.repository.SplashRepository

class CheckUpdateUseCaseImpl(private val repository: SplashRepository):CheckUpdateUseCase {
    override suspend fun checkUpdate(): LastVersionDomain {
        return repository.checkUpdate()
    }
}