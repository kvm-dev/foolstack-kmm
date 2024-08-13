package ru.foolstack.impl.domain.interactor

import ru.foolstack.splash.api.domain.usecase.GetCurrentLanguageUseCase
import ru.foolstack.splash.api.domain.usecase.GetNetworkStateUseCase

class SplashInteractor(
    private val getLocalUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase
//    private val getNetworkConnectionUseCase: GetNetworkConnectionUseCase,
//    private val getUserTokenUseCase: GetTokenUseCase,
//    private val addTokenUseCase: AddTokenUseCase,
//    private val clearTokensUseCase: ClearTokensUseCase,
//    private val updateTokenUseCase: UpdateTokenUseCase
) {
        fun getCurrentLang() = getLocalUseCase.getCurrentLang()
//    fun getLocal() = getLocalUseCase.getUseCase()
        fun getLocal() = "RU"
        fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()
//
//    fun getNetworkStatus() = getNetworkConnectionUseCase.getConnectionStatus()
//
//    suspend fun getUserToken(): String = withContext(
//        Dispatchers.IO){
//        return@withContext getUserTokenUseCase.invoke()
//    }
//
//    suspend fun addUserToken(token: String) = withContext(Dispatchers.IO){
//        addTokenUseCase.invoke(token = token)
//    }
//
//    suspend fun clearUserToken() = withContext(Dispatchers.IO){
//        clearTokensUseCase.invoke()
//    }
//
//    suspend fun updateUserToken(newToken: String) = withContext(Dispatchers.IO){
//        updateTokenUseCase.invoke(newToken = newToken)
//    }
}