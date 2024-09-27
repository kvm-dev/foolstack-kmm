package ru.foolstack.networkconnection.api.domain

interface GetNetworkStateUseCase {
    fun isNetworkAvailable():Boolean
}