package ru.foolstack.splash.api.domain.usecase

interface GetNetworkStateUseCase {
    fun isNetworkAvailable():Boolean
}