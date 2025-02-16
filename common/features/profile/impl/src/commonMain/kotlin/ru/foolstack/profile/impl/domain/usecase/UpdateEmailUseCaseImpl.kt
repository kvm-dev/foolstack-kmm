package ru.foolstack.profile.impl.domain.usecase

import ru.foolstack.profile.api.domain.usecase.UpdateEmailUseCase
import ru.foolstack.profile.api.model.UpdateEmailResponseDomain
import ru.foolstack.profile.impl.data.repository.ProfileRepository

class UpdateEmailUseCaseImpl(private val repository: ProfileRepository): UpdateEmailUseCase {
    override suspend fun updateEmail(email: String): UpdateEmailResponseDomain {
        return repository.updateEmail(email = email)
    }
}