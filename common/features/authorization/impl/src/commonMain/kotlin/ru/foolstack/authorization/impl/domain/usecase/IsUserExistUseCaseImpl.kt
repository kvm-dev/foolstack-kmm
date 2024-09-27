package ru.foolstack.authorization.impl.domain.usecase

import ru.foolstack.authorization.api.domain.usecase.IsUserExistUseCase
import ru.foolstack.authorization.api.model.IsUserExistDomain
import ru.foolstack.authorization.impl.data.repository.AuthorizationRepository

class IsUserExistUseCaseImpl(private val repository: AuthorizationRepository):IsUserExistUseCase {
    override suspend fun isUserExist(email: String): IsUserExistDomain {
        return repository.isUserExist(email = email)
    }
}