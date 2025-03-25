package ru.foolstack.profile.api.domain.usecase

import ru.foolstack.profile.api.model.UpdateEmailResponseDomain
import ru.foolstack.profile.api.model.UpdateNameResponseDomain

interface UpdateEmailUseCase {
    suspend fun updateEmail(email: String): UpdateEmailResponseDomain
}