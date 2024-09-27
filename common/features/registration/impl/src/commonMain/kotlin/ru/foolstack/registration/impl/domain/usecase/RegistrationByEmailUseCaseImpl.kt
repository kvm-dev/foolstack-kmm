package ru.foolstack.registration.impl.domain.usecase

import ru.foolstack.registration.api.domain.usecase.RegistrationByEmailUseCase
import ru.foolstack.registration.api.model.RegistrationByEmailDomain
import ru.foolstack.registration.impl.data.repository.RegistrationRepository

class RegistrationByEmailUseCaseImpl(private val repository: RegistrationRepository): RegistrationByEmailUseCase {
    override suspend fun registration(email: String): RegistrationByEmailDomain {
        return repository.registrationByEmail(email)
    }
}