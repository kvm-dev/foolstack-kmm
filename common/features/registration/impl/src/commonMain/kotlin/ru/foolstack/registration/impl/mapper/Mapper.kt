package ru.foolstack.registration.impl.mapper

import ru.foolstack.registration.api.model.RegistrationByEmailDomain
import ru.foolstack.registration.impl.model.response.RegistrationByEmailResponse

class Mapper {

    fun map(response: RegistrationByEmailResponse): RegistrationByEmailDomain{
        return RegistrationByEmailDomain(
            success = response.success,
            errorMsg = response.errorMsg
        )
    }
}