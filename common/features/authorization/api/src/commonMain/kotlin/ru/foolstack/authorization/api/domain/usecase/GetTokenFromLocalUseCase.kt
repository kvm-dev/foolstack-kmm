package ru.foolstack.authorization.api.domain.usecase

interface GetTokenFromLocalUseCase {

    suspend fun getToken(): String
}