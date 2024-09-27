package ru.foolstack.registration.impl.data.repository

import ru.foolstack.registration.api.model.RegistrationByEmailDomain
import ru.foolstack.registration.impl.data.repository.network.NetworkDataSource

class RegistrationRepository(private val networkDataSource: NetworkDataSource) {

    suspend fun registrationByEmail(email: String): RegistrationByEmailDomain {
            return networkDataSource.registrationByEmail(email = email)
        }
    }