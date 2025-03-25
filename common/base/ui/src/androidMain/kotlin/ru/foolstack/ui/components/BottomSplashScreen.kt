package ru.foolstack.ui.components

import android.os.CountDownTimer
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.foolstack.ui.theme.BottomScreenBackground

@Composable
fun BottomSplashScreen(
    bottomSplashScreenState: BottomSplashScreenState,
    splashBitmap: ImageBitmap,
    logoBitmap: ImageBitmap,
    onClickGuestScreen: () -> Unit = {},
    onClickAuthorizationScreen: () -> Unit = {},
    onChangeEmail: (String) -> Unit = {},
    onChangeOtp: (String) -> Unit = {},
    onClickAuthorizationOrRegistrationByEmail: () -> Unit = {},
    onClickConfirm: (Boolean) -> Unit = {},
    onClickResend: () -> Unit = {},
    onClickTryAgain: () -> Unit = {},
    onClickBackToAuthorizationScreen: () -> Unit = {},
    onClickBackToEmailScreen: () -> Unit = {},
    titleText: String = "",
    descriptionText:String = "",
    mainButtonText: String = "",
    secondButtonText: String = "",
    resendButtonText: String = "",
    emailText: String = "",
    errorText: String = "",
    isEmailError: Boolean = false,
    isEmailLoading: Boolean = false,
    otpValue: String = "",
    isOtpError: Boolean = false,
    isOtpLoading: Boolean = false,
    isUserExist: Boolean = false){
    var offset by remember { mutableFloatStateOf(0f) }
    var isAnimated by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = isAnimated, label = "transition")
    val bottomHeight by transition.animateDp(transitionSpec = {
        tween(3*1000)
    }, "") { animated ->
        if (!animated) 0.dp else 1000.dp
    }
    val shape = RoundedCornerShape(32.dp, 32.dp)
    Column(modifier = Modifier
        .fillMaxSize()
        .scrollable(
            orientation = Orientation.Vertical,
            state = rememberScrollableState { delta ->
                offset += delta
                delta
            },
        )
        .imePadding(),
        verticalArrangement = Arrangement.Bottom) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(bottomHeight)
                .clip(shape)
                .background(MaterialTheme.colorScheme.BottomScreenBackground),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ScrollableContent(
                bottomSplashScreenState = bottomSplashScreenState,
                logoBitmap = logoBitmap,
                splashBitmap = splashBitmap,
                onClickGuestScreen = onClickGuestScreen,
                onClickTryAgain = onClickTryAgain,
                onClickAuthorizationScreen = onClickAuthorizationScreen,
                onchangeEmail = onChangeEmail,
                onchangeOtp = onChangeOtp,
                onClickAuthorizationOrRegistrationByEmail = onClickAuthorizationOrRegistrationByEmail,
                onClickConfirm = onClickConfirm,
                onClickResend = onClickResend,
                onClickBackToAuthorizationScreen = onClickBackToAuthorizationScreen,
                onClickBackToEmailScreen = onClickBackToEmailScreen,
                titleText = titleText,
                descriptionText = descriptionText,
                mainButtonText = mainButtonText,
                secondButtonText = secondButtonText,
                emailText = emailText,
                errorText = errorText,
                isEmailError = isEmailError,
                isEmailLoading = isEmailLoading,
                otpValue = otpValue,
                isOtpError = isOtpError,
                isOtpLoading = isOtpLoading,
                resendButtonText = resendButtonText,
                isUserExist = isUserExist
            )
        }
    }

    val timer = object : CountDownTimer(1000, 500) {
        override fun onTick(seconds: Long) {
            isAnimated = true
            cancel()
        }
        override fun onFinish() {
            cancel()
        }
    }
    timer.start()
}

@Composable
private fun ScrollableContent(
    bottomSplashScreenState: BottomSplashScreenState,
    splashBitmap: ImageBitmap,
    logoBitmap: ImageBitmap,
    titleText: String,
    descriptionText: String,
    onClickGuestScreen: () -> Unit = {},
    onClickAuthorizationScreen: () -> Unit = {},
    onchangeEmail: (String) -> Unit = {},
    onchangeOtp: (String) -> Unit = {},
    onClickAuthorizationOrRegistrationByEmail: () -> Unit = {},
    onClickConfirm: (Boolean) -> Unit = {},
    onClickResend: () -> Unit = {},
    onClickTryAgain: () -> Unit = {},
    onClickBackToAuthorizationScreen: () -> Unit = {},
    onClickBackToEmailScreen: () -> Unit = {},
    mainButtonText: String = "",
    secondButtonText: String = "",
    emailText: String = "",
    errorText: String = "",
    isEmailError: Boolean = false,
    isEmailLoading: Boolean = false,
    otpValue: String = "",
    isOtpError: Boolean = false,
    isOtpLoading: Boolean = false,
    resendButtonText: String = "",
    isUserExist: Boolean = false
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    LaunchedEffect(key1 = keyboardHeight) {
        coroutineScope.launch {
            scrollState.scrollBy(keyboardHeight.toFloat())
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(100.dp, 10.dp),
            bitmap = if(bottomSplashScreenState == BottomSplashScreenState.UNAUTHORIZED ||
                bottomSplashScreenState == BottomSplashScreenState.NO_CONNECTION) splashBitmap else {logoBitmap},
            contentDescription = "FoolStack"
        )
        Spacer(modifier = Modifier.height(10.dp))
        Title(text = titleText, modifier = Modifier)
        Spacer(modifier = Modifier.height(10.dp))
        BaseText(text = descriptionText)
        Spacer(modifier = Modifier.weight(1f))
        if(bottomSplashScreenState == BottomSplashScreenState.AUTHORIZATION){
            EmailTextField(
                modifier = Modifier
                    .padding(16.dp),
                value = emailText,
                placeholder = "Email",
                onChange = onchangeEmail,
                isError = isEmailError,
                errorMessage = errorText,
                isEnabled = !isEmailLoading
            )
        }
        if(bottomSplashScreenState == BottomSplashScreenState.CONFIRM){
                Spacer(modifier = Modifier.height(10.dp))
                OtpTextField(otpText = otpValue, onOtpTextChange = onchangeOtp,
                    errorMessage = errorText, isError = isOtpError, isEnabled = !isOtpLoading)
        }
        when(bottomSplashScreenState){
            BottomSplashScreenState.UNAUTHORIZED-> {
                YellowButton(
                    onClick = { onClickAuthorizationScreen() },
                    text = mainButtonText,
                    isEnabled = true,
                    isLoading = false,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth())
            }

            BottomSplashScreenState.AUTHORIZATION-> {
                if(emailText.isEmpty() || isEmailError){
                    YellowButton(
                        onClick = {},
                        text = mainButtonText,
                        isEnabled = false,
                        isLoading = false,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth())
                }
                else{
                    YellowButton(
                        onClick = { onClickAuthorizationOrRegistrationByEmail() },
                        text = mainButtonText,
                        isEnabled = true,
                        isLoading = isEmailLoading,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth())
                }
            }
            BottomSplashScreenState.CONFIRM-> {
                Spacer(modifier = Modifier.height(40.dp))
                if(otpValue.isEmpty() || isOtpError || otpValue.length!=4){
                    YellowButton(
                        onClick = {},
                        text = mainButtonText,
                        isEnabled = false,
                        isLoading = isEmailLoading,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth())
                }
                else{
                    if(isOtpLoading){
                        LoadingIndicator()
                    }
                    else{
                        YellowButton(
                            onClick = { onClickConfirm(isUserExist) },
                            text = mainButtonText,
                            isEnabled = true,
                            isLoading = isEmailLoading,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth())
                    }
                }
                var resendText = resendButtonText
                var timeLeft by remember { mutableIntStateOf(300) }
                LaunchedEffect(key1 = timeLeft) {
                    while (timeLeft > 0) {
                        delay(1000L)
                        timeLeft--
                    }
                }
                    resendText = if(timeLeft>0){
                    resendText.replace("seconds", timeLeft.toString())
                } else {
                    resendButtonText.split(" (")[0]
                }
                if(!isOtpLoading && isUserExist){
                    SecondOrangeButton(text = resendText, onClick = { timeLeft = 300; onClickResend() }, isEnabled = timeLeft==0)
                }
            }
            BottomSplashScreenState.NO_CONNECTION->{
             //nothing, without buttons
            }
            BottomSplashScreenState.ANY_ERROR->{
                YellowButton(
                    onClick = { onClickTryAgain() },
                    text = mainButtonText,
                    isEnabled = true,
                    isLoading = false,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth())
            }
        }
        if(bottomSplashScreenState != BottomSplashScreenState.NO_CONNECTION){
           val onclickEvent = when(bottomSplashScreenState){
                BottomSplashScreenState.UNAUTHORIZED-> onClickGuestScreen
                BottomSplashScreenState.CONFIRM-> onClickBackToEmailScreen
                else -> onClickBackToAuthorizationScreen
            }
            SecondOrangeButton(text = secondButtonText, onClick = { onclickEvent() }, isEnabled = true)
        }
    }
}

enum class BottomSplashScreenState {
    UNAUTHORIZED, AUTHORIZATION, CONFIRM, NO_CONNECTION, ANY_ERROR
}

