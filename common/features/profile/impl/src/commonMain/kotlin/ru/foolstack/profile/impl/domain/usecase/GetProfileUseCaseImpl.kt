package ru.foolstack.profile.impl.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.foolstack.profile.api.domain.usecase.GetProfileUseCase
import ru.foolstack.profile.api.model.ProfileDomain
import ru.foolstack.profile.impl.data.repository.ProfileRepository
import ru.foolstack.utils.model.ResultState

class GetProfileUseCaseImpl(private val repository: ProfileRepository):GetProfileUseCase {

    private val _profile = MutableStateFlow<ResultState<ProfileDomain>>(
        ResultState.Idle)
    override val profileState = _profile.asStateFlow()
    override suspend fun getProfile(fromLocal: Boolean): ProfileDomain {
        _profile.tryEmit(ResultState.Loading)
        return if(fromLocal){
            val cachedProfile = repository.getProfileFromLocal()
            _profile.tryEmit(ResultState.Success(cachedProfile))
            cachedProfile
        }
        else{
            val networkResult = repository.getProfileFromServer()
            _profile.tryEmit(ResultState.Success(networkResult))
             networkResult
        }
    }
}