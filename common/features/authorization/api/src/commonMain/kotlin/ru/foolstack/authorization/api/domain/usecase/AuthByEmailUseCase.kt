package ru.foolstack.authorization.api.domain.usecase

import ru.foolstack.authorization.api.model.AuthByEmailDomain

interface AuthByEmailUseCase {

    suspend fun auth(email: String, code: String): AuthByEmailDomain
}