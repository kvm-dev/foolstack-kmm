package ru.foolstack.authorization.impl.domain.usecase

import ru.foolstack.authorization.api.domain.usecase.ConfirmAuthAndRegUseCase
import ru.foolstack.authorization.api.model.ConfirmAuthAndRegDomain
import ru.foolstack.authorization.impl.data.repository.AuthorizationRepository

class ConfirmAuthAndRegUseCaseImpl(private val repository: AuthorizationRepository):ConfirmAuthAndRegUseCase {
    override suspend fun confirm(email: String, code: String): ConfirmAuthAndRegDomain {
        return repository.confirmAuthAndReg(email = email, code = code)
    }
}