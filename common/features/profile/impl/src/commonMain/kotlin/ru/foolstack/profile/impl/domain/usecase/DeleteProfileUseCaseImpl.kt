package ru.foolstack.profile.impl.domain.usecase

import ru.foolstack.profile.api.domain.usecase.DeleteProfileUseCase
import ru.foolstack.profile.api.model.DeleteProfileResponseDomain
import ru.foolstack.profile.impl.data.repository.ProfileRepository

class DeleteProfileUseCaseImpl(private val repository: ProfileRepository): DeleteProfileUseCase {

    override suspend fun deleteProfile(): DeleteProfileResponseDomain {
        return repository.deleteProfile()
    }
}