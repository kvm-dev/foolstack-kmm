package ru.foolstack.authorization.api.domain.usecase

import ru.foolstack.authorization.api.model.IsUserExistDomain

interface IsUserExistUseCase {

    suspend fun isUserExist(email:String):IsUserExistDomain

}