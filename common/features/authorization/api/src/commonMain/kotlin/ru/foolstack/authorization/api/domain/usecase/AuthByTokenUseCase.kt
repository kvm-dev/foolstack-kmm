package ru.foolstack.authorization.api.domain.usecase

import ru.foolstack.authorization.api.model.AuthByTokenDomain

interface AuthByTokenUseCase {

    suspend fun auth(): AuthByTokenDomain

}