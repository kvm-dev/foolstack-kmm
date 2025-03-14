package ru.foolstack.splash.impl.presentation.ui

import android.util.Log
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
import ru.foolstack.splash.impl.presentation.viewmodel.SplashViewModel
import ru.foolstack.model.ProgressState
import ru.foolstack.ui.components.BigAppTitle
import ru.foolstack.ui.components.BottomSplashScreen
import ru.foolstack.ui.components.BottomSplashScreenState
import ru.foolstack.ui.components.SplashBackground

@Composable
fun SplashScreen(theme: String, navigateToMainScreen: () -> Unit, splashViewModel: SplashViewModel = koinViewModel()) {
    SplashBackground(theme)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val viewModelState by splashViewModel.progressState.collectAsState()
        when (viewModelState) {
            ProgressState.LOADING -> {
                //nothing because loading
                Log.d("мы здесь", "лоадинг")
                splashViewModel.initViewModel()
            }

            ProgressState.COMPLETED -> {
                val uiState by splashViewModel.uiState.collectAsState()
                val splashBitmap = ImageBitmap.imageResource(ru.foolstack.ui.R.drawable.splash_img)
                val logoBitmap = ImageBitmap.imageResource(ru.foolstack.ui.R.drawable.logo)
                BigAppTitle("foolStack")
                when (uiState) {
                    is SplashViewState.Idle->{
                        //nothing init state
                    }
                    is SplashViewState.UnAuthorized -> {
                        val state = uiState as SplashViewState.UnAuthorized
                        BottomSplashScreen(
                            bottomSplashScreenState = BottomSplashScreenState.UNAUTHORIZED,
                            splashBitmap = splashBitmap,
                            logoBitmap = logoBitmap,
                            onClickGuestScreen = {
                                splashViewModel.refreshProfile()
                                navigateToMainScreen()
                            },
                            onClickAuthorizationScreen = { splashViewModel.authorizationOrRegistrationSplashScreen() },
                            titleText = state.splashBottomText.splashTitleText,
                            descriptionText = state.splashBottomText.splashDescriptionText,
                            mainButtonText = state.splashBottomText.splashMainButtonText,
                            secondButtonText = state.splashBottomText.splashSecondButtonText,
                            resendButtonText = state.splashBottomText.splashResendButtonText,
                        )
                    }

                    is SplashViewState.NoConnectionError -> {
                        val state = uiState as SplashViewState.NoConnectionError
                        BottomSplashScreen(
                            bottomSplashScreenState = BottomSplashScreenState.NO_CONNECTION,
                            splashBitmap = splashBitmap,
                            logoBitmap = logoBitmap,
                            onChangeEmail = splashViewModel::setEmail,
                            onChangeOtp = splashViewModel::setOtp,
                            onClickTryAgain = { splashViewModel.onClickTryAgain() },
                            titleText = state.splashTitleText,
                            descriptionText = state.splashDescriptionText,
                        )
                    }

                    is SplashViewState.AuthorizationOrRegistration -> {
                        val state = uiState as SplashViewState.AuthorizationOrRegistration
                        BottomSplashScreen(
                            bottomSplashScreenState = BottomSplashScreenState.AUTHORIZATION,
                            splashBitmap = splashBitmap,
                            logoBitmap = logoBitmap,
                            onClickAuthorizationScreen = { splashViewModel.authorizationOrRegistrationSplashScreen() },
                            onClickGuestScreen = {
                                splashViewModel.refreshProfile()
                                navigateToMainScreen()
                            },
                            onChangeEmail = splashViewModel::setEmail,
                            onClickAuthorizationOrRegistrationByEmail = { splashViewModel.authorizationOrRegistrationByEmail() },
                            onClickBackToAuthorizationScreen = { splashViewModel.backToAuthorizationScreen() },
                            titleText = state.splashBottomText.splashTitleText,
                            descriptionText = state.splashBottomText.splashDescriptionText,
                            mainButtonText = state.splashBottomText.splashMainButtonText,
                            secondButtonText = state.splashBottomText.splashSecondButtonText,
                            emailText = splashViewModel.emailValue,
                            errorText = splashViewModel.emailError,
                            isEmailError = splashViewModel.emailError.isNotEmpty(),
                            isEmailLoading = splashViewModel.emailLoading,
                        )
                    }

                    is SplashViewState.Confirmation -> {
                        val state = uiState as SplashViewState.Confirmation
                        BottomSplashScreen(
                            bottomSplashScreenState = BottomSplashScreenState.CONFIRM,
                            splashBitmap = splashBitmap,
                            logoBitmap = logoBitmap,
                            onClickConfirm = splashViewModel::confirmAuthOrRegistrationByEmail,
                            onClickResend = { splashViewModel.resendOtpCode() },
                            onChangeEmail = splashViewModel::setEmail,
                            onChangeOtp = splashViewModel::setOtp,
                            onClickBackToEmailScreen = { splashViewModel.backToEmailScreen() },
                            titleText = state.splashBottomText.splashTitleText,
                            descriptionText = state.splashBottomText.splashDescriptionText,
                            mainButtonText = state.splashBottomText.splashMainButtonText,
                            secondButtonText = state.splashBottomText.splashSecondButtonText,
                            resendButtonText = state.splashBottomText.splashResendButtonText,
                            errorText = splashViewModel.otpError,
                            otpValue = splashViewModel.otpValue,
                            isOtpError = splashViewModel.otpError.isNotEmpty(),
                            isOtpLoading = splashViewModel.otpLoading,
                            isUserExist = state.isUserExist
                        )
                    }

                    is SplashViewState.Authorized -> {
                        navigateToMainScreen()
                    }

                    is SplashViewState.AnyError -> {
                        splashViewModel.onClickTryAgain()
                    }
                }
            }
        }
    }
}
