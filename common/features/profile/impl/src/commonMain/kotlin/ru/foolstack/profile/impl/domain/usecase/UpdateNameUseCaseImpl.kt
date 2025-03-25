package ru.foolstack.profile.impl.domain.usecase

import ru.foolstack.profile.api.domain.usecase.UpdateNameUseCase
import ru.foolstack.profile.api.model.UpdateNameResponseDomain
import ru.foolstack.profile.impl.data.repository.ProfileRepository

class UpdateNameUseCaseImpl(private val repository: ProfileRepository): UpdateNameUseCase {
    override suspend fun updateName(userName: String): UpdateNameResponseDomain {
        return repository.updateName(name = userName)
    }
}