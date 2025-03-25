package ru.foolstack.profile.impl.domain.usecase

import ru.foolstack.profile.api.domain.usecase.UpdatePhotoUseCase
import ru.foolstack.profile.api.model.UpdatePhotoResponseDomain
import ru.foolstack.profile.impl.data.repository.ProfileRepository

class UpdatePhotoUseCaseImpl(private val repository: ProfileRepository): UpdatePhotoUseCase {

    override suspend fun updatePhoto(photo: ByteArray): UpdatePhotoResponseDomain {
        return repository.updatePhoto(photo = photo)
    }
}