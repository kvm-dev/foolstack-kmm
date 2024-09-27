package ru.foolstack.authorization.impl.domain.usecase

import ru.foolstack.authorization.api.domain.usecase.AuthByEmailUseCase
import ru.foolstack.authorization.api.model.AuthByEmailDomain
import ru.foolstack.authorization.impl.data.repository.AuthorizationRepository

class AuthByEmailUseCaseImpl(private val repository: AuthorizationRepository):AuthByEmailUseCase {
    override suspend fun auth(email: String, code: String): AuthByEmailDomain {
        return repository.authByEmail(email = email, code = code)
    }
}