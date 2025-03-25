package ru.foolstack.authorization.impl.domain.usecase

import ru.foolstack.authorization.api.domain.usecase.GuestAuthUseCase
import ru.foolstack.authorization.impl.data.repository.AuthorizationRepository

class GuestAuthUseCaseImpl(private val repository: AuthorizationRepository):GuestAuthUseCase {
    override suspend fun authLog() {
        repository.loginByGuestLog()
    }
}