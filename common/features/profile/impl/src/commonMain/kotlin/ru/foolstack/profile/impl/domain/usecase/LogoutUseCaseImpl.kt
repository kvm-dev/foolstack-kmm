package ru.foolstack.profile.impl.domain.usecase

import ru.foolstack.profile.api.domain.usecase.LogoutUseCase
import ru.foolstack.profile.impl.data.repository.ProfileRepository

class LogoutUseCaseImpl(private val repository: ProfileRepository):LogoutUseCase {
    override suspend fun logout() {
        repository.logout()
    }
}