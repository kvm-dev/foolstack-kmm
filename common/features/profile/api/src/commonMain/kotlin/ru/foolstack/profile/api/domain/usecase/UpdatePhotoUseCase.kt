package ru.foolstack.profile.api.domain.usecase

import ru.foolstack.profile.api.model.UpdatePhotoResponseDomain

interface UpdatePhotoUseCase {
    suspend fun updatePhoto(photo: ByteArray): UpdatePhotoResponseDomain
}