package ru.foolstack.profile.api.domain.usecase

import ru.foolstack.profile.api.model.UpdateNameResponseDomain

interface UpdateNameUseCase {
    suspend fun updateName(userName: String):UpdateNameResponseDomain
}