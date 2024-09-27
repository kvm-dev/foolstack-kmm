package ru.foolstack.authorization.impl.domain.usecase

import ru.foolstack.authorization.api.domain.usecase.AuthByTokenUseCase
import ru.foolstack.authorization.api.model.AuthByTokenDomain
import ru.foolstack.authorization.impl.data.repository.AuthorizationRepository

class AuthByTokenUseCaseImpl(private val repository: AuthorizationRepository):AuthByTokenUseCase {
    override suspend fun auth(): AuthByTokenDomain {
        return repository.authByToken()
    }
}