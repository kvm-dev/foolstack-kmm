package ru.foolstack.profile.impl.domain.usecase

import ru.foolstack.profile.api.domain.usecase.GetProfileUseCase
import ru.foolstack.profile.api.model.ProfileDomain
import ru.foolstack.profile.impl.data.repository.ProfileRepository

class GetProfileUseCaseImpl(private val repository: ProfileRepository):GetProfileUseCase {
    override suspend fun getProfile(fromLocal: Boolean): ProfileDomain {
        return if(fromLocal){
            repository.getProfileFromLocal()
        }
        else{
            repository.getProfileFromServer()
        }
    }
}