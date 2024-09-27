package ru.foolstack.registration.api.domain.usecase

import ru.foolstack.registration.api.model.RegistrationByEmailDomain

interface RegistrationByEmailUseCase {

    suspend fun registration(email: String): RegistrationByEmailDomain
}