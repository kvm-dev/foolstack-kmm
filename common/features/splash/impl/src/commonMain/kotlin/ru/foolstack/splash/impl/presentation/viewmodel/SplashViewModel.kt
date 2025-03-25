package ru.foolstack.splash.impl.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.foolstack.splash.impl.domain.interactor.SplashInteractor
import ru.foolstack.splash.impl.presentation.ui.SplashBottomText
import ru.foolstack.splash.impl.presentation.ui.SplashViewState
import ru.foolstack.model.ProgressState
import ru.foolstack.utils.TextFieldValidation
import ru.foolstack.utils.model.ResultState
import ru.foolstack.viewmodel.BaseViewModel

class SplashViewModel(private val interactor: SplashInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<SplashViewState>(
        SplashViewState.Idle
    )
    val uiState: StateFlow<SplashViewState> = _uiState.asStateFlow()

    private var viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob + coroutineExceptionHandler)

    var emailValue by mutableStateOf("")
        private set
    var emailError by mutableStateOf("")
        private set
    var emailLoading by mutableStateOf(false)
        private set

    var otpValue by mutableStateOf("")
        private set
    var otpError by mutableStateOf("")
        private set
    var otpLoading by mutableStateOf(false)
        private set

    fun initViewModel()  {
        if (progressState.value == ProgressState.LOADING) {
            uiScope.launch {
                val local = interactor.getCurrentLang()
                val isTokenExist = interactor.isTokenExist()
                val isInternetConnected = interactor.isConnectionAvailable()
                if(isInternetConnected){
                    interactor.getProfessionsFromServer()
                }
                else{
                    interactor.getProfessionsFromLocal()
                }
                if (isTokenExist) {
                    if (isInternetConnected) {
                        //authByToken
                        val profile = interactor.getProfileFromServer()
                        val events = interactor.getEventsFromServer()
                        if(interactor.validateAuthorizedData(
                                profile = profile,
                                events = events)){
                            val state = interactor.getAuthorizedState(
                                local = local,
                                isInternetConnected = isInternetConnected,
                                profile = profile,
                                events = events)
                            _uiState.update { state }
                        }
                        else{
                            val state = interactor.authorizedErrorsResponseHandler(
                                profile = profile,
                                events = events)
                            _uiState.update { state }
                        }
                    } else {
                        //sendLog
                        interactor.authByTokenOfflineLog()
                        //goToMain without AuthCheck
                        val profile = interactor.getProfileFromLocal()
                        val events = interactor.getEventsFromLocal()
                        if (profile.userId != 0) {
                            val state = interactor.getAuthorizedState(
                                local = local,
                                isInternetConnected = isInternetConnected,
                                profile = profile,
                                events = events)
                            _uiState.update { state }
                        } else {
                            //showConnectionErrorBottomSheet
                            _uiState.update { interactor.getNoConnectionState() }
                            checkConnectionCycle()
                        }
                    }
                } else {
                    if(isInternetConnected){
                        //showAuthorizationBottomSheet
                        val state = interactor.getUnauthorizedState()
                        interactor.getEventsFromServer()
                        _uiState.update { state }
                    }
                    else{
                        //showConnectionErrorBottomSheet
                        _uiState.update { interactor.getNoConnectionState() }
                        checkConnectionCycle()
                    }
                }
            }
        }
        updateState(ProgressState.COMPLETED)
    }

    private fun validateEmail(): Boolean {
        val lang = interactor.getCurrentLang().lang
        val email = emailValue.trim()
        var isValid = true
        var errorMessage = ""
        if (email.isBlank() || email.isEmpty()) {
            errorMessage =
                interactor.getErrorEmailEmpty(lang = lang)
            isValid = false
        } else if (!TextFieldValidation.validateEmail(email)) {
            errorMessage =
                interactor.getErrorEmailIncorrect(lang = lang)
            isValid = false
        }
        emailError = errorMessage
        return isValid
    }

    fun setEmail(value: String) {
        emailValue = value
        emailLoading = false
        emailError = ""
        validateEmail()
    }

    fun setOtp(value: String) {
        otpError = ""
        otpLoading = false
        if (value.length <= 4) {
            otpValue = value
        }
    }

    fun authorizationOrRegistrationSplashScreen() {
        val state = interactor.getAuthorizationOrRegistrationState()
        _uiState.update { state }
    }

    fun authorizationOrRegistrationByEmail() = with(viewModelScope + coroutineExceptionHandler) {
        emailLoading = true
        val local = interactor.getCurrentLang()
        launch {
            val isUserExist = interactor.isUserExist(emailValue)
            if (isUserExist.success) {
                val state = interactor.getConfirmationState(
                    isUserExist = isUserExist.success
                )
                _uiState.update { state }
            } else {
                if (isUserExist.errorMsg.lowercase() == interactor.getErrorEmailEmptyOrIncorrect().lowercase()
                ) {
                    emailError =
                        interactor.getErrorEmailEmptyOrIncorrect(
                            local.lang
                        )
                } else if (isUserExist.errorMsg.lowercase() == interactor.getErrorCodeAlreadySent().lowercase()) {
                    val state = interactor.getConfirmationState(isUserExist = true )
                    otpError = interactor.getErrorCodeAlreadySent(lang = local.lang)
                    _uiState.update { state }
                }
                else if(isUserExist.errorMsg.lowercase() == interactor.getErrorUserUnconfirmed().lowercase()){
                    val state = interactor.getConfirmationState(isUserExist = false )
                    _uiState.update { state }
                }
                else if (isUserExist.errorMsg.lowercase() == interactor.getErrorUserNotFound().lowercase() ||
                    isUserExist.errorMsg.lowercase() == interactor.getErrorMethodNotFound("RU").lowercase()){
                    val registrationResult = interactor.registrationByEmail(emailValue)
                    if(registrationResult.success){
                        val state = interactor.getConfirmationState(isUserExist = false)
                        _uiState.update { state }
                    }
                    else{
                        if(registrationResult.errorMsg.lowercase() ==
                            interactor.getErrorUserExist()
                                .lowercase()){
                            emailError = interactor.getErrorUserExist(local.lang)
                        }
                        else if (registrationResult.errorMsg.lowercase() ==
                            interactor.getErrorEmailEmptyOrIncorrect().lowercase()
                        ){
                            emailError = interactor.getErrorEmailEmptyOrIncorrect(local.lang)
                        }
                        else if (registrationResult.errorMsg.lowercase() ==
                            interactor.getErrorCodeEmptyOrIncorrect().lowercase()){
                            emailError = interactor.getErrorCodeEmptyOrIncorrect(local.lang)
                        }
                        else {
                            emailError = registrationResult.errorMsg
                        }
                    }
                }
                else {
                    emailError = isUserExist.errorMsg
                }
            }
            emailLoading = false
        }
    }

    fun confirmAuthOrRegistrationByEmail(isUserExist: Boolean) = with(viewModelScope + coroutineExceptionHandler) {
        otpLoading = true
        val isInternetConnected = interactor.isConnectionAvailable()
        val local = interactor.getCurrentLang()
        launch {
            val profile = interactor.getProfileFromServer()
            val events = interactor.getEventsFromServer()
            if(isUserExist){
                val result = interactor.authByEmail(email = emailValue, code  = otpValue)
                if(result.errorMsg.isEmpty()){
                    if(interactor.validateAuthorizedData(
                            profile = profile,
                            events = events)){
                        val state = interactor.getAuthorizedState(
                            local = local,
                            isInternetConnected = isInternetConnected,
                            profile = profile,
                            events = events)
                        _uiState.update { state }
                    }
                    else{
                        val state = interactor.authorizedErrorsResponseHandler(
                            profile = profile,
                            events = events)
                        _uiState.update { state }
                    }
                }
                else{
                    if(result.errorMsg.lowercase() == interactor.getErrorUserUnconfirmed().lowercase()){
                        val resultConfirm = interactor.confirmAuthAndReg(email = emailValue, code = otpValue)
                        if(resultConfirm.success){
                            if(interactor.validateAuthorizedData(
                                    profile = profile,
                                    events = events)){
                                val state = interactor.getAuthorizedState(
                                    local = local,
                                    isInternetConnected = isInternetConnected,
                                    profile = profile,
                                    events = events)
                                _uiState.update { state }
                            }
                            else
                            {
                                val state = interactor.authorizedErrorsResponseHandler(
                                    profile = profile,
                                    events = events)
                                _uiState.update { state }
                            }
                        }
                        else{
                            otpError = if(resultConfirm.errorMsg.lowercase() == interactor.getErrorEmailNotFoundOrInvalidCodeOrUserConfirmed().lowercase()){
                                interactor.getErrorEmailNotFoundOrInvalidCodeOrUserConfirmed(local.lang)
                            } else if (resultConfirm.errorMsg.lowercase() == interactor.getErrorCodeEmptyOrIncorrect().lowercase()){
                                interactor.getErrorCodeEmptyOrIncorrect(local.lang)
                            } else if (resultConfirm.errorMsg.lowercase() == interactor.getErrorEmailEmptyOrIncorrect().lowercase()){
                                interactor.getErrorEmailEmptyOrIncorrect(local.lang)
                            } else{
                                resultConfirm.errorMsg
                            }
                        }
                    }
                    else{
                        otpError = when(result.errorMsg.lowercase()){
                            interactor.getErrorCodeEmptyOrIncorrect().lowercase()->{
                                interactor.getErrorCodeEmptyOrIncorrect(local.lang)
                            }
                            interactor.getErrorUserNotFound().lowercase()->{
                                interactor.getErrorUserNotFound(local.lang)
                            }
                            interactor.getErrorCodeWrongOrExpired().lowercase()->{
                                interactor.getErrorCodeWrongOrExpired(local.lang)
                            }
                            else->{
                                result.errorMsg
                            }
                        }
                    }
                }
            }
            else{
                val resultConfirm = interactor.confirmAuthAndReg(email = emailValue, code = otpValue)
                if(resultConfirm.success){
                    if(interactor.validateAuthorizedData(
                            profile = profile,
                            events = events)){
                        val state = interactor.getAuthorizedState(
                            local = local,
                            isInternetConnected = isInternetConnected,
                            profile = profile,
                            events = events)
                        _uiState.update { state }
                    }
                    else{
                        val state = interactor.authorizedErrorsResponseHandler(
                            profile = profile,
                            events = events)
                        _uiState.update { state }
                    }
                }
                else{
                    otpError = if(resultConfirm.errorMsg.lowercase() == interactor.getErrorEmailNotFoundOrInvalidCodeOrUserConfirmed().lowercase()){
                        interactor.getErrorEmailNotFoundOrInvalidCodeOrUserConfirmed(local.lang)
                    } else if (resultConfirm.errorMsg.lowercase() == interactor.getErrorCodeEmptyOrIncorrect().lowercase()){
                        interactor.getErrorCodeEmptyOrIncorrect(local.lang)
                    } else if (resultConfirm.errorMsg.lowercase() == interactor.getErrorEmailEmptyOrIncorrect().lowercase()){
                        interactor.getErrorEmailEmptyOrIncorrect(local.lang)
                    } else{
                        resultConfirm.errorMsg
                    }
                }
            }
            otpLoading = false
        }
    }

    fun resendOtpCode() = with(viewModelScope + coroutineExceptionHandler) {
        otpValue = ""
        otpError = ""
        launch {
            interactor.isUserExist(emailValue)
        }
    }

    private suspend fun checkConnectionCycle(){
        var connectionStatus = interactor.isConnectionAvailable()
        while (!connectionStatus){
            connectionStatus = interactor.isConnectionAvailable()
            delay(3000)
        }
        if(connectionStatus){
            updateState(ProgressState.LOADING)
            initViewModel()
        }
    }

    fun onClickTryAgain(){
        updateState(ProgressState.LOADING)
        initViewModel()
    }

    fun backToAuthorizationScreen(){
        _uiState.update { interactor.getUnauthorizedState() }
    }

    fun backToEmailScreen(){
        _uiState.update { interactor.getAuthorizationOrRegistrationState() }
    }

    fun loginByGuestLog() =  with(viewModelScope + coroutineExceptionHandler){
        launch {
            interactor.loginByGuestLog()
        }
    }

    fun refreshProfile() = with(viewModelScope + coroutineExceptionHandler){
       launch {
           if(interactor.isConnectionAvailable()){
               interactor.getProfileFromServer()
               if(interactor.eventsState.value !is ResultState.Success){
                   interactor.getEventsFromServer()
               }
           }
           else{
               interactor.getProfileFromLocal()
               if(interactor.eventsState.value !is ResultState.Success){
                   interactor.getEventsFromLocal()
               }
           }
       }
    }
}