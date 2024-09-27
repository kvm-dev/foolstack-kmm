package ru.foolstack.splash.impl.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.foolstack.splash.impl.domain.interactor.SplashInteractor
import ru.foolstack.splash.impl.presentation.ui.SplashBottomText
import ru.foolstack.splash.impl.presentation.ui.SplashViewState
import ru.foolstack.model.ProgressState
import ru.foolstack.utils.TextFieldValidation
import ru.foolstack.viewmodel.BaseViewModel

class SplashViewModel(private val interactor: SplashInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<SplashViewState>(
        SplashViewState.UnAuthorized(
            "ENG",
            false,
            splashBottomText = SplashBottomText()
        )
    )
    val uiState: StateFlow<SplashViewState> = _uiState.asStateFlow()

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

    fun initViewModel() = with(viewModelScope) {
        if (progressState.value == ProgressState.LOADING) {
            launch {
                val local = interactor.getCurrentLang()
                val isTokenExist = interactor.isTokenExist()
                val isInternetConnected = interactor.isConnectionAvailable()
                if (isTokenExist) {
                    if (isInternetConnected) {
                        //authByToken
                        val profile = interactor.getProfileFromServer()
                        val materials = interactor.getMaterialsFromServer()
                        val tests = interactor.getTestsFromServer()
                        val books = interactor.getBooksFromServer()
                        val news = interactor.getNewsFromServer()
                        val events = interactor.getEventsFromServer()
                        val studies = interactor.getStudiesFromServer()
                        if(interactor.validateAuthorizedData(
                            profile = profile,
                            materials = materials,
                            tests = tests,
                            books = books,
                            news = news,
                            events = events,
                            studies = studies
                        )){
                            val state = interactor.getAuthorizedState(
                                local = local,
                                isInternetConnected = isInternetConnected,
                                profile = profile,
                                materials = materials,
                                tests =  tests,
                                books = books,
                                news = news,
                                events = events,
                                studies = studies)
                            _uiState.update { state }
                            updateState(ProgressState.COMPLETED)
                        }
                        else{
                            val state = interactor.authorizedErrorsResponseHandler(
                                profile = profile,
                                materials = materials,
                                tests = tests,
                                books = books,
                                news = news,
                                events = events,
                                studies = studies
                            )
                            _uiState.update { state }
                            updateState(ProgressState.COMPLETED)
                        }
                    } else {
                        //goToMain without AuthCheck
                        val profile = interactor.getProfileFromLocal()
                        val materials = interactor.getMaterialsFromLocal()
                        val tests = interactor.getTestsFromLocal()
                        val books = interactor.getBooksFromLocal()
                        val news = interactor.getNewsFromLocal()
                        val events = interactor.getEventsFromLocal()
                        val studies = interactor.getStudiesFromLocal()
                        if (profile.userId != 0 && materials.materials.isNotEmpty() && tests.tests.isNotEmpty()) {
                           val state = interactor.getAuthorizedState(
                               local = local,
                               isInternetConnected = isInternetConnected,
                               profile = profile,
                               materials = materials,
                               tests = tests,
                               news = news,
                               books = books,
                               events = events,
                               studies = studies
                               )
                            _uiState.update { state }
                            updateState(ProgressState.COMPLETED)
                        } else {
                            //showConnectionErrorBottomSheet
                            _uiState.update { interactor.getNoConnectionState() }
                            updateState(ProgressState.COMPLETED)
                            checkConnectionCycle()
                        }
                    }
                } else {
                    if(isInternetConnected){
                        //showAuthorizationBottomSheet
                        val state = interactor.getUnauthorizedState()
                        _uiState.update { state }
                        updateState(ProgressState.COMPLETED)
                    }
                    else{
                        //showConnectionErrorBottomSheet
                        _uiState.update { interactor.getNoConnectionState() }
                        updateState(ProgressState.COMPLETED)
                        checkConnectionCycle()
                    }
                }
            }
        }
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

    fun authorizationOrRegistrationByEmail() = with(viewModelScope) {
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

    fun confirmAuthOrRegistrationByEmail(isUserExist: Boolean) = with(viewModelScope) {
        otpLoading = true
        val isInternetConnected = interactor.isConnectionAvailable()
        val local = interactor.getCurrentLang()
        launch {
            val profile = interactor.getProfileFromServer()
            val materials = interactor.getMaterialsFromServer()
            val tests = interactor.getTestsFromServer()
            val books = interactor.getBooksFromServer()
            val news = interactor.getNewsFromServer()
            val events = interactor.getEventsFromServer()
            val studies = interactor.getStudiesFromServer()
            if(isUserExist){
                val result = interactor.authByEmail(email = emailValue, code  = otpValue)
                if(result.errorMsg.isEmpty()){
                    if(interactor.validateAuthorizedData(
                        profile = profile,
                        materials = materials,
                        tests = tests,
                        books = books,
                        news = news,
                        events = events,
                        studies = studies
                    )){
                        val state = interactor.getAuthorizedState(
                            local = local,
                            isInternetConnected = isInternetConnected,
                            profile = profile,
                            materials = materials,
                            tests = tests,
                            books = books,
                            news = news,
                            events = events,
                            studies = studies)
                        _uiState.update { state }
                    }
                    else{
                        val state = interactor.authorizedErrorsResponseHandler(
                            profile = profile,
                            materials = materials,
                            tests = tests,
                            books = books,
                            news = news,
                            events = events,
                            studies = studies
                        )
                        _uiState.update { state }
                    }
                }
                else{
                    if(result.errorMsg.lowercase() == interactor.getErrorUserUnconfirmed().lowercase()){
                        val resultConfirm = interactor.confirmAuthAndReg(email = emailValue, code = otpValue)
                        if(resultConfirm.success){
                            if(interactor.validateAuthorizedData(
                                    profile = profile,
                                    materials = materials,
                                    tests = tests,
                                    books = books,
                                    news = news,
                                    events = events,
                                    studies = studies
                                )){
                                val state = interactor.getAuthorizedState(
                                    local = local,
                                    isInternetConnected = isInternetConnected,
                                    profile = profile,
                                    materials = materials,
                                    tests = tests,
                                    books = books,
                                    news = news,
                                    events = events,
                                    studies = studies)
                                _uiState.update { state }
                            }
                            else
                            {
                                val state = interactor.authorizedErrorsResponseHandler(
                                    profile = profile,
                                    materials = materials,
                                    tests = tests,
                                    books = books,
                                    news = news,
                                    events = events,
                                    studies = studies
                                )
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
                            books = books,
                            materials = materials,
                            tests = tests,
                            news = news,
                            events = events,
                            studies = studies)){
                        val state = interactor.getAuthorizedState(
                            local = local,
                            isInternetConnected = isInternetConnected,
                            profile = profile,
                            materials = materials,
                            tests = tests,
                            news = news,
                            events = events,
                            studies = studies,
                            books = books
                            )
                        _uiState.update { state }
                    }
                    else{
                        val state = interactor.authorizedErrorsResponseHandler(
                            profile = profile,
                            books = books,
                            materials = materials,
                            tests = tests,
                            news = news,
                            events = events,
                            studies = studies
                        )
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

    fun resendOtpCode() = with(viewModelScope) {
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
}