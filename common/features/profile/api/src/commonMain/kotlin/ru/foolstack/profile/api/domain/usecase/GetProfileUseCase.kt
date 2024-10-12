package ru.foolstack.profile.api.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import ru.foolstack.profile.api.model.ProfileDomain
import ru.foolstack.utils.model.ResultState

interface GetProfileUseCase {

    val profileState: StateFlow<ResultState<ProfileDomain>>
    suspend fun getProfile(fromLocal: Boolean = false):ProfileDomain
}