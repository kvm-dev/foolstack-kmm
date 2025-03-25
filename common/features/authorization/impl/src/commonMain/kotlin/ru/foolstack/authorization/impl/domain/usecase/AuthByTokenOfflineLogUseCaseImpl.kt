package ru.foolstack.authorization.impl.domain.usecase

import ru.foolstack.authorization.api.domain.usecase.AuthByTokenOfflineLogUseCase
import ru.foolstack.authorization.impl.data.repository.AuthorizationRepository

class AuthByTokenOfflineLogUseCaseImpl(private val repository: AuthorizationRepository):AuthByTokenOfflineLogUseCase {
    override fun logOfflineAuthBytToken() {
        repository.authByTokenOfflineLog()
    }
}