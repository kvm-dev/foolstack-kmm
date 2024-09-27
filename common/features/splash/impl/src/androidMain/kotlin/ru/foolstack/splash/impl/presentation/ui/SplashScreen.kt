package ru.foolstack.splash.impl.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import org.koin.androidx.compose.koinViewModel
import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.splash.impl.presentation.viewmodel.SplashViewModel
import ru.foolstack.model.ProgressState
import ru.foolstack.ui.components.BigAppTitle
import ru.foolstack.ui.components.BottomSplashScreen
import ru.foolstack.ui.components.BottomSplashScreenState
import ru.foolstack.ui.components.SplashBackground

@Composable
fun SplashScreen(navigateToMainScreen: () -> Unit, splashViewModel: SplashViewModel = koinViewModel())
 {
    SplashBackground()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        splashViewModel.initViewModel()
        val viewModelState by splashViewModel.progressState.collectAsState()
        when(viewModelState){
            ProgressState.LOADING ->{
                //nothing because loading
            }
            ProgressState.COMPLETED ->{
                val uiState by splashViewModel.uiState.collectAsState()
                UiStateHandler(
                    state = uiState,
                    authScreen = navigateToMainScreen,
                    onClickAuthorizationScreen = { splashViewModel.authorizationOrRegistrationSplashScreen() },
                    onClickConfirm = splashViewModel::confirmAuthOrRegistrationByEmail,
                    onClickResend = { splashViewModel.resendOtpCode() },
                    onChangeEmail =  splashViewModel::setEmail,
                    onChangeOtp =  splashViewModel::setOtp,
                    onClickAuthorizationOrRegistration = { splashViewModel.authorizationOrRegistrationByEmail() },
                    onClickTryAgain = {splashViewModel.onClickTryAgain()},
                    onClickBackToEmailScreen = { splashViewModel.backToEmailScreen() },
                    onClickBackToAuthorizationScreen = { splashViewModel.backToAuthorizationScreen() },
                    navigateToAuthorizedScreen = navigateToMainScreen,
                    emailText = splashViewModel.emailValue,
                    isEmailError = splashViewModel.emailError.isNotEmpty(),
                    emailErrorText = splashViewModel.emailError,
                    isEmailLoading = splashViewModel.emailLoading,
                    otpText = splashViewModel.otpValue,
                    otpErrorText = splashViewModel.otpError,
                    isOtpError = splashViewModel.otpError.isNotEmpty(),
                    isOtpLoading = splashViewModel.otpLoading)
                        }
                    }
                }
            }
@Composable private fun UiStateHandler(state: SplashViewState,
                                       authScreen: () -> Unit,
                                       onClickAuthorizationScreen: () -> Unit,
                                       onChangeEmail: (String) -> Unit,
                                       onChangeOtp: (String) -> Unit,
                                       onClickAuthorizationOrRegistration: () -> Unit,
                                       onClickConfirm: (Boolean) -> Unit,
                                       onClickResend: () -> Unit,
                                       onClickTryAgain: () -> Unit,
                                       onClickBackToEmailScreen: () -> Unit,
                                       onClickBackToAuthorizationScreen: () -> Unit,
                                       navigateToAuthorizedScreen: () -> Unit,
                                       emailText: String,
                                       isEmailError: Boolean,
                                       emailErrorText: String,
                                       isEmailLoading: Boolean,
                                       otpText: String,
                                       isOtpError: Boolean,
                                       otpErrorText: String,
                                       isOtpLoading: Boolean
                                       ){
    val splashBitmap = ImageBitmap.imageResource(ru.foolstack.ui.R.drawable.splash_img)
    val logoBitmap = ImageBitmap.imageResource(ru.foolstack.ui.R.drawable.logo)
    val bugBitmap = ImageBitmap.imageResource(id = ru.foolstack.ui.R.drawable.bug_icon)
    BigAppTitle("foolStack")
    when(state){
        is SplashViewState.UnAuthorized -> {
                BottomSplashScreen(
                    bottomSplashScreenState = BottomSplashScreenState.UNAUTHORIZED,
                    splashBitmap = splashBitmap,
                    logoBitmap = logoBitmap,
                    onClickGuestScreen = authScreen,
                    onClickAuthorizationScreen = onClickAuthorizationScreen,
                    titleText = state.splashBottomText.splashTitleText,
                    descriptionText  = state.splashBottomText.splashDescriptionText,
                    mainButtonText = state.splashBottomText.splashMainButtonText,
                    secondButtonText = state.splashBottomText.splashSecondButtonText,
                    resendButtonText = state.splashBottomText.splashResendButtonText,
                )

        }
        is SplashViewState.NoConnectionError -> {
            BottomSplashScreen(
                bottomSplashScreenState = BottomSplashScreenState.NO_CONNECTION,
                splashBitmap = splashBitmap,
                logoBitmap = logoBitmap,
                onChangeEmail = onChangeEmail,
                onChangeOtp = onChangeOtp,
                onClickTryAgain = onClickTryAgain,
                titleText = state.splashTitleText,
                descriptionText = state.splashDescriptionText,
            )
        }

        is SplashViewState.AuthorizationOrRegistration-> {
            BottomSplashScreen(
                bottomSplashScreenState = BottomSplashScreenState.AUTHORIZATION,
                splashBitmap = splashBitmap,
                logoBitmap = logoBitmap,
                onClickAuthorizationScreen = onClickAuthorizationScreen,
                onClickGuestScreen = authScreen,
                onChangeEmail = onChangeEmail,
                onClickAuthorizationOrRegistrationByEmail = onClickAuthorizationOrRegistration,
                onClickBackToAuthorizationScreen = onClickBackToAuthorizationScreen,
                titleText = state.splashBottomText.splashTitleText,
                descriptionText  = state.splashBottomText.splashDescriptionText,
                mainButtonText = state.splashBottomText.splashMainButtonText,
                secondButtonText = state.splashBottomText.splashSecondButtonText,
                emailText = emailText,
                errorText = emailErrorText,
                isEmailError = isEmailError,
                isEmailLoading = isEmailLoading,
            )
        }

        is SplashViewState.Confirmation-> {
            BottomSplashScreen(
                bottomSplashScreenState = BottomSplashScreenState.CONFIRM,
                splashBitmap = splashBitmap,
                logoBitmap = logoBitmap,
                onClickConfirm = onClickConfirm,
                onClickResend = onClickResend,
                onChangeEmail = onChangeEmail,
                onChangeOtp = onChangeOtp,
                onClickBackToEmailScreen = onClickBackToEmailScreen,
                titleText = state.splashBottomText.splashTitleText,
                descriptionText  = state.splashBottomText.splashDescriptionText,
                mainButtonText = state.splashBottomText.splashMainButtonText,
                secondButtonText = state.splashBottomText.splashSecondButtonText,
                resendButtonText = state.splashBottomText.splashResendButtonText,
                errorText = otpErrorText,
                otpValue = otpText,
                isOtpError = isOtpError,
                isOtpLoading = isOtpLoading,
                isUserExist = state.isUserExist
            )
        }

        is SplashViewState.Authorized-> {
            val data = state
            navigateToAuthorizedScreen()
        }

        is SplashViewState.AnyError-> {
            BottomSplashScreen(
                bottomSplashScreenState = BottomSplashScreenState.ANY_ERROR,
                splashBitmap = splashBitmap,
                logoBitmap = bugBitmap,
                onClickTryAgain = onClickTryAgain,
                titleText = state.splashTitleText,
                descriptionText  = state.splashDescriptionText,
                mainButtonText = state.tryAgainButtonText,
            )
        }
    }
}