package ru.foolstack.splash.api.domain.usecase

import ru.foolstack.splash.api.model.TokenValidateResultDomain

interface ValidateUserTokenUseCase {
    fun validateUserToken():TokenValidateResultDomain
}