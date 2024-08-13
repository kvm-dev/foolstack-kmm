package ru.foolstack.authorization.impl.domain.interactor

class AuthorizationInteractor(
//    private val getLocalUseCase: GetLocalUseCase,
//    private val getNetworkConnectionUseCase: GetNetworkConnectionUseCase,
//    private val getUserTokenUseCase: GetTokenUseCase,
//    private val addTokenUseCase: AddTokenUseCase,
//    private val clearTokensUseCase: ClearTokensUseCase,
//    private val updateTokenUseCase: UpdateTokenUseCase
) {
    //    fun getLocal() = getLocalUseCase.getUseCase()
    fun getLocal() = "RU"
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