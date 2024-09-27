package ru.foolstack.authorization.impl.domain.usecase

import ru.foolstack.authorization.api.domain.usecase.GetTokenFromLocalUseCase
import ru.foolstack.authorization.impl.data.repository.AuthorizationRepository

class GetTokenFromLocalUseCaseImpl(private val repository: AuthorizationRepository):GetTokenFromLocalUseCase {
    override suspend fun getToken(): String {
        return repository.getTokenFromLocal()
    }
}