package ru.foolstack.profile.api.domain.usecase

import ru.foolstack.profile.api.model.DeleteProfileResponseDomain

interface DeleteProfileUseCase {
    suspend fun deleteProfile(): DeleteProfileResponseDomain
}