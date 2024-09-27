package ru.foolstack.profile.impl.data.repository.local

import ru.foolstack.profile.api.model.ProfileDomain
import ru.foolstack.profile.impl.mapper.Mapper
import ru.foolstack.storage.DatabaseSdk

class LocalDataSource(private val databaseSdk: DatabaseSdk, private val mapper: Mapper) {
    suspend fun getProfile():ProfileDomain {
        val profile = databaseSdk.getProfile()
       return mapper.mapToProfileDomain(profile = profile, profile.userPhotoBase64)
    }
    suspend fun saveProfile(profile:ProfileDomain){
        databaseSdk.saveProfile(profile = mapper.mapFromProfileDomain(profile, profile.userPhotoBase64))
    }
}