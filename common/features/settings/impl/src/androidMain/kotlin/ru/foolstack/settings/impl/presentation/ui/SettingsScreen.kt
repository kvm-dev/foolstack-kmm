package ru.foolstack.settings.impl.presentation.ui

import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.model.ProgressState
import ru.foolstack.settings.api.model.AppThemeDomain
import ru.foolstack.settings.impl.presentation.viewmodel.SettingsViewModel
import ru.foolstack.ui.R
import ru.foolstack.ui.components.Avatar
import ru.foolstack.ui.components.EmailTextField
import ru.foolstack.ui.components.GreenDialog
import ru.foolstack.ui.components.RadioChips
import ru.foolstack.ui.components.SecondGreenButton
import ru.foolstack.ui.components.ShimmerEffect
import ru.foolstack.ui.components.SubTitle
import ru.foolstack.ui.components.Title
import ru.foolstack.ui.components.TopBar
import ru.foolstack.ui.components.UserNameTextField
import ru.foolstack.ui.components.YellowButton
import ru.foolstack.ui.model.Chip

@Composable
fun SettingsScreen(recreateAction:()->Unit, settingsViewModel: SettingsViewModel = koinViewModel(), navController: NavController, onDeleteAction: ()-> Unit) {
    val backDispatcher =
        checkNotNull(LocalOnBackPressedDispatcherOwner.current).onBackPressedDispatcher

    val viewModelState by settingsViewModel.progressState.collectAsState()
    when (viewModelState) {
        ProgressState.COMPLETED -> {
            Log.d("settings Complete", "yes")
            val settingsState by settingsViewModel.uiState.collectAsState()
            when (settingsState) {
                is SettingsViewState.LoadingState -> {
                    Log.d("settings state is", "Loading")
                    LoadingScreen(lang = settingsViewModel.getCurrentLang())
                }

                is SettingsViewState.ErrorState -> {
                    Log.d("settings in state is", "Error")
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(top = 40.dp)
                    ) {
                        TopBar(
                            screenTitle = if ((settingsState as SettingsViewState.ErrorState).lang is LangResultDomain.Ru) {
                                "Настройки"
                            } else {
                                "Settings"
                            }, action = { backDispatcher.onBackPressed() })
                        Column(modifier = Modifier
                            .align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            val bugBitmap = ImageBitmap.imageResource(id = R.drawable. bug_icon)
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(100.dp, 10.dp),
                                bitmap = bugBitmap,
                                contentDescription = "FoolStack"
                            )
                            Title(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = if ((settingsState as SettingsViewState.ErrorState).lang is LangResultDomain.Ru) {
                                    "Что-то пошло не так..."
                                } else {
                                    "Something went wrong..."
                                }
                            )
                            YellowButton(
                                onClick = { backDispatcher.onBackPressed() },
                                text = if ((settingsState as SettingsViewState.ErrorState).lang is LangResultDomain.Ru) {
                                    "Назад"
                                } else {
                                    "Go back"
                                },
                                isEnabled = true,
                                isLoading = false,
                                modifier = Modifier
                                    .padding(top = 30.dp)
                            )
                        }
                    }
                }

                is SettingsViewState.SuccessState -> {
                    Log.d("settings in state is", "Success")
                    val successState = (settingsState as SettingsViewState.SuccessState)
                    //ui
                    val isShowConfirmChangeEmailDialog  = remember { mutableStateOf(false) }
                    val isShowConfirmDeleteAccountDialog  = remember { mutableStateOf(false) }
                    val isShowNoConnectionDialog  = remember { mutableStateOf(false) }
                    val isShowSuccessDialog  = remember { mutableStateOf(false) }
                    isShowSuccessDialog.value = successState.isShowSuccessDialog
                    val isShowFailDialog  = remember { mutableStateOf(false) }
                    isShowFailDialog.value = successState.isShowErrorDialog
                    Column(modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()) {
                        TopBar(
                            screenTitle = if ((settingsState as SettingsViewState.SuccessState).lang is LangResultDomain.Ru) {
                                "Настройки"
                            } else {
                                "Settings"
                            }, action = { backDispatcher.onBackPressed() })

                        Avatar(avatar = successState.userPhoto, modifier = Modifier, onClick = {
                            if(settingsViewModel.isConnectionAvailable()){
                                if (it != null) {
                                    settingsViewModel.updatePhoto(it)
                                }
                            }
                            else{
                                isShowNoConnectionDialog.value = true
                            }
                        })

                        UserNameTextField(
                            modifier = Modifier
                                .padding(top = 24.dp, start = 16.dp, end = 16.dp),
                            value = settingsViewModel.userNameValue,
                            placeholder = if(successState.lang is LangResultDomain.Ru){"Твое имя"} else{"Your name"},
                            onChange = settingsViewModel::setUserName,
                            isError = settingsViewModel.userNameError.isNotEmpty(),
                            errorMessage = settingsViewModel.userNameError,
                            isEnabled = true
                        )

                        EmailTextField(
                            modifier = Modifier
                                .padding(top = 24.dp, start = 16.dp, end = 16.dp),
                            value = settingsViewModel.emailValue,
                            placeholder = "Email",
                            onChange = settingsViewModel::setEmail,
                            isError = settingsViewModel.emailError.isNotEmpty(),
                            errorMessage = settingsViewModel.emailError,
                            isEnabled = true
                        )

                        YellowButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            text = if(successState.lang is LangResultDomain.Ru){ "Сохранить" } else { "Save" },

                            onClick = {
                                        if(settingsViewModel.isConnectionAvailable()){
                                            if(settingsViewModel.emailValue.isNotEmpty() && successState.userEmail != settingsViewModel.emailValue){
                                                isShowConfirmChangeEmailDialog.value = true
                                            }
                                            else{
                                                settingsViewModel.updateUserData()
                                            }
                                        }
                                else{
                                    isShowNoConnectionDialog.value = true
                                        }
                            },
                            isEnabled = successState.userName != settingsViewModel.userNameValue || successState.userEmail != settingsViewModel.emailValue,
                            isLoading = false
                        )

                        SecondGreenButton(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 16.dp),
                            text = if(successState.lang is LangResultDomain.Ru) { "Удалить учетную запись" } else { "Delete account" },
                            isEnabled = true,
                            onClick = {
                                if(settingsViewModel.isConnectionAvailable()){
                                    isShowConfirmDeleteAccountDialog.value = true
                                }
                                else{
                                    isShowNoConnectionDialog.value = true
                                }
                                })

                        SubTitle(
                            text = if(successState.lang is LangResultDomain.Ru) { "Твоя тема" } else { "Your theme" },
                            modifier = Modifier
                                .padding(top = 28.dp, start = 16.dp, end = 16.dp)
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                        )
                        val chips = ArrayList<Chip>()
                        if(successState.lang is LangResultDomain.Ru){
                            chips.add(
                                Chip(
                                id = 1,
                                name = "Системная"
                                )
                            )
                            chips.add(Chip(
                                id = 1,
                                name = "Светлая"
                            ))
                            chips.add(Chip(
                                id = 1,
                                name = "Темная"
                            ))
                        }
                        else{
                            chips.add(
                                Chip(
                                    id = 1,
                                    name = "System"
                                )
                            )
                            chips.add(Chip(
                                id = 1,
                                name = "Light"
                            ))
                            chips.add(Chip(
                                id = 1,
                                name = "Dark"
                            ))
                        }
                        val currentTheme = isSystemInDarkTheme()
                        Log.d("currentTheme is", "$currentTheme")
                        val selectedChip  = remember { mutableStateOf("") }
                        selectedChip.value = when(successState.appTheme){
                            AppThemeDomain.SYSTEM_THEME -> {
                                if(successState.lang is LangResultDomain.Ru){ "Системная" } else { "System" }
                            }
                            AppThemeDomain.DARK_THEME -> {
                                if(successState.lang is LangResultDomain.Ru){ "Темная" } else { "Dark" }
                            }
                            AppThemeDomain.LIGHT_THEME -> {
                                if(successState.lang is LangResultDomain.Ru){ "Светлая" } else { "Light" }
                            }
                        }
                        RadioChips(chips = chips, selectedChip = selectedChip, onclickChip = { selectedChip.value = it
                        settingsViewModel.setAppTheme(it)
                        recreateAction()
                        })
                    }
                    GreenDialog(
                        title = settingsViewModel.getConfirmChangeEmailDialogTitle(),
                        text = settingsViewModel.getConfirmChangeEmailDialogText(),
                        generalActionText = settingsViewModel.getConfirmChangeEmailDialogActionBtn(),
                        secondaryActionText = settingsViewModel.getConfirmChangeEmailDialogCancelBtn(),
                        onGeneralActionClick = {
                            isShowConfirmChangeEmailDialog.value = false
                            settingsViewModel.updateUserData()
                        },
                        onSecondaryActionClick = {
                            isShowConfirmChangeEmailDialog.value = false
                        },
                        isVisible = isShowConfirmChangeEmailDialog
                    )

                    GreenDialog(
                        title = settingsViewModel.getConfirmDeleteAccountDialogTitle(),
                        text = settingsViewModel.getConfirmDeleteAccountDialogText(),
                        generalActionText = settingsViewModel.getConfirmDeleteAccountDialogActionBtn(),
                        secondaryActionText = settingsViewModel.getConfirmDeleteAccountDialogCancelBtn(),
                        onGeneralActionClick = {
                            onDeleteAction()
                            settingsViewModel.deleteProfile()
                            isShowConfirmDeleteAccountDialog.value = false
                        },
                        onSecondaryActionClick = {
                            isShowConfirmDeleteAccountDialog.value = false
                        },
                        isVisible = isShowConfirmDeleteAccountDialog
                    )

                    GreenDialog(
                        title = settingsViewModel.getSuccessDialogTitle(),
                        text = settingsViewModel.getSuccessDialogText(),
                        generalActionText = settingsViewModel.getSuccessDialogBtn(),
                        hideSecondaryButton = true,
                        onGeneralActionClick = {
                            settingsViewModel.hideStateDialogs()
                            settingsViewModel.updateProfile()
                        },
                        onSecondaryActionClick = {
                            isShowSuccessDialog.value = false
                            settingsViewModel.hideStateDialogs()
                        },
                        isVisible = isShowSuccessDialog
                    )

                    GreenDialog(
                        title = settingsViewModel.getFailDialogTitle(),
                        text = settingsViewModel.getFailDialogText(),
                        generalActionText = settingsViewModel.getFailDialogBtn(),
                        hideSecondaryButton = true,
                        onGeneralActionClick = {
                            settingsViewModel.hideStateDialogs()
                        },
                        onSecondaryActionClick = {
                            isShowFailDialog.value = false
                            settingsViewModel.hideStateDialogs()
                        },
                        isVisible = isShowFailDialog
                    )

                    GreenDialog(
                        title = settingsViewModel.getNoConnectionDialogTitle(),
                        text = settingsViewModel.getNoConnectionDialogText(),
                        generalActionText = settingsViewModel.getNoConnectionDialogBtn(),
                        hideSecondaryButton = true,
                        onGeneralActionClick = {
                            isShowNoConnectionDialog.value = false
                            settingsViewModel.hideStateDialogs()
                        },
                        onSecondaryActionClick = {
                            isShowNoConnectionDialog.value = false
                            settingsViewModel.hideStateDialogs()
                        },
                        isVisible = isShowNoConnectionDialog
                    )
                }
            }
        }

        ProgressState.LOADING -> {
            Log.d("setting realy loading", "true")
            LoadingScreen(lang = settingsViewModel.getCurrentLang())
            settingsViewModel.initViewModel()
        }

        else -> {
            Log.d("settings realy complete", "no")
            settingsViewModel.initViewModel()
        }
    }
}

@Composable
fun LoadingScreen(lang: LangResultDomain){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
    ) {
        TopBar(
            screenTitle = if (lang is LangResultDomain.Ru) {
                "Настройки"
            } else {
                "Settings"
            }, action = {  },
            isIconVisible = false)

            Column(modifier = Modifier
                .padding(top = 72.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                ShimmerEffect(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.LightGray, shape = CircleShape),
                    durationMillis = 1000
                )

                ShimmerEffect(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                        .background(Color.LightGray, RoundedCornerShape(20)),
                    durationMillis = 1000
                )

                ShimmerEffect(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                        .background(Color.LightGray, RoundedCornerShape(20)),
                    durationMillis = 1000
                )

                ShimmerEffect(
                    modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .background(Color.LightGray, RoundedCornerShape(20)),
                    durationMillis = 1000
                )

                ShimmerEffect(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                        .padding(start = 36.dp, end = 36.dp, top = 24.dp)
                        .background(Color.LightGray, RoundedCornerShape(20)),
                    durationMillis = 1000
                )

                ShimmerEffect(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                        .padding(start = 36.dp, end = 36.dp, top = 24.dp)
                        .background(Color.LightGray, RoundedCornerShape(20)),
                    durationMillis = 1000
                )

                Row(modifier = Modifier
                    .padding(horizontal = 24.dp)){
                    repeat(3){
                        ShimmerEffect(
                            modifier = Modifier
                                .height(68.dp)
                                .width(100.dp)
                                .padding(start = 4.dp, end = 4.dp, top = 36.dp)
                                .background(Color.LightGray, RoundedCornerShape(20)),
                            durationMillis = 1000
                        )
                    }

                }
            }
    }
}