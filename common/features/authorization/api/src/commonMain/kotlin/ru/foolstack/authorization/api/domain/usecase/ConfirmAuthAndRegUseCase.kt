package ru.foolstack.authorization.api.domain.usecase

import ru.foolstack.authorization.api.model.ConfirmAuthAndRegDomain

interface ConfirmAuthAndRegUseCase {

    suspend fun confirm(email: String, code: String):ConfirmAuthAndRegDomain
}