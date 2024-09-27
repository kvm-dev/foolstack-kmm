package ru.foolstack.profile.api.domain.usecase

import ru.foolstack.profile.api.model.ProfileDomain

interface GetProfileUseCase {
    suspend fun getProfile(fromLocal: Boolean = false):ProfileDomain
}