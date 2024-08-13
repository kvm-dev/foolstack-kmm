package ru.foolstack.android.splash.impl.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.androidx.compose.koinViewModel
import ru.foolstack.android.splash.impl.R
import ru.foolstack.android.ui.components.BigAppTitle
import ru.foolstack.android.ui.components.BottomSplashScreen
import ru.foolstack.android.ui.components.SplashBackground
import ru.foolstack.android.ui.components.SplashNotFoundConnection
import ru.foolstack.impl.domain.interactor.SplashInteractor
import ru.foolstack.impl.presentation.viewmodel.SplashViewModel

@Composable
fun SplashScreen(authScreen: () -> Unit, viewModel: SplashViewModel = viewModel { SplashViewModel(
    SplashInteractor()
) }) {
    SplashBackground()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BigAppTitle("FoolStack")
        splashViewModel.initViewModel()
        val viewModelState by splashViewModel.progressState.collectAsState()
        when(viewModelState){
            ProgressState.LOADING ->{
                //nothing because loading
            }
            ProgressState.COMPLETED ->{
//                val locale = splashViewModel.getLocal()
                val locale = "RU"
                val isAuthorized by splashViewModel.authorizedState.collectAsState()
                val connection by splashViewModel.connectionState.collectAsState()

                when(isAuthorized){
                    SplashViewState.AuthorizedState.AUTHORIZED->{

                    }
                    SplashViewState.AuthorizedState.UNAUTHORIZED->{
                        when(connection){
                            SplashViewState.ConnectionState.CONNECTED->{
                                val bitmap = ImageBitmap.imageResource(R.drawable.splash_img)
                                if(locale=="RU"){
                                    BottomSplashScreen(bitmap, authScreen, authScreen, "Поможем найти работу",
                                        "Техническое собеседование или скрининг с HR? - Поможем подготовиться.\n" +
                                                "\n" +
                                                "Не хватает практики? Проверь себя в наших тестовых заданиях и получи обратную связь.",
                                        "Войти в АЙТИ",
                                        "Войти как гость")
                                }
                                else{
                                    BottomSplashScreen(bitmap, authScreen, authScreen, "We will help you to find a job",
                                        "Technical interview or screening with HR? - Let's help you get ready.\n" +
                                                "\n" +
                                                "Do not enough practice? Get test yourself in our test tasks and get feedback.",
                                        "Join IT",
                                        "Login as guest")
                                }
                            }
                            SplashViewState.ConnectionState.DISCONNECTED->{
                                if(locale=="RU"){
                                    SplashNotFoundConnection(text1 = "Отсутствует соединение с интернетом", text2 = "Проверьте подключение")
                                }
                                else{
                                    SplashNotFoundConnection(text1 = "Internet connection is not found", text2 = "Check connection")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}