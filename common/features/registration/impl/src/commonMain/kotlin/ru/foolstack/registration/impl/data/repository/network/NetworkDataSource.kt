package ru.foolstack.registration.impl.data.repository.network

import ru.foolstack.registration.api.model.RegistrationByEmailDomain
import ru.foolstack.registration.impl.mapper.Mapper

class NetworkDataSource(private val api: RegistrationApi, private val mapper: Mapper){

    suspend fun registrationByEmail(email: String): RegistrationByEmailDomain {
        return mapper.map(api.registrationByEmail(email = email))
    }
}