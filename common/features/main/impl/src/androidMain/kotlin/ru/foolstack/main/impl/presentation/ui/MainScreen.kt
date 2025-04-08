package ru.foolstack.main.impl.presentation.ui

import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import ru.foolstack.main.impl.mapper.Mapper
import ru.foolstack.main.impl.presentation.viewmodel.MainViewModel
import ru.foolstack.model.ProgressState
import ru.foolstack.ui.components.AchievementsSlider
import ru.foolstack.ui.components.EventHorizontalSlider
import ru.foolstack.ui.components.MainTopBar
import ru.foolstack.ui.components.UserType
import ru.foolstack.ui.model.Lang
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.ui.components.GreenDialog
import ru.foolstack.ui.components.ShimmerEffect
import ru.foolstack.ui.components.SubMenu
import ru.foolstack.ui.components.Title
import ru.foolstack.ui.theme.LoadingIndicatorBackground
import ru.foolstack.ui.theme.MainYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = koinViewModel(),
    onClickEvents: () -> Unit = {},
    onClickBooks: () -> Unit = {},
    onclickStudies: () -> Unit = {},
    onclickSettings: () -> Unit = {},
    onClickLogout: () -> Unit,
    navController: NavController,
    theme: String,
    eventDestination: String) {
    val eventId  = remember { mutableIntStateOf(0) }
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val isShowGuestNotificationDialog  = remember { mutableStateOf(true) }

    val state = rememberPullToRefreshState()

    val scaleFraction = {
        if (isRefreshing) 1f
        else LinearOutSlowInEasing.transform(state.distanceFraction).coerceIn(0f, 1f)
    }

    val onRefresh: () -> Unit = {
            isRefreshing = true
            coroutineScope.launch {
                // fetch something
                mainViewModel.refresh()
                delay(2000)
                isRefreshing = false
            }
    }

    val viewModelState by mainViewModel.progressState.collectAsState()
    when(viewModelState) {
        ProgressState.COMPLETED -> {
            val isShowAchievementDialog  = remember { mutableStateOf(false) }
            val selectedAchievement  = remember { mutableIntStateOf(0) }
            Log.d("realy Complete", "yes")
            val authStatus by mainViewModel.uiState.collectAsState()
            when(authStatus){
                is MainViewState.AuthorizedClient-> {
                    Log.d("user is ", "Client")
                    isShowGuestNotificationDialog.value = false
                    val clientState = authStatus as MainViewState.AuthorizedClient
                    Column(
                        modifier = Modifier
                            .pullToRefresh(
                                state = if(mainViewModel.isConnectionAvailable()) { state } else {
                                    PullToRefreshState()
                                },
                                isRefreshing = isRefreshing,
                                onRefresh = { if(mainViewModel.isConnectionAvailable()){
                                    onRefresh()
                                }
                                }
                            )
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())) {
                        Box(
                            Modifier
                                .align(Alignment.CenterHorizontally)
                                .graphicsLayer {
                                    scaleX = scaleFraction()
                                    scaleY = scaleFraction()
                                }
                                .zIndex(1F)
                        ) {
                            PullToRefreshDefaults.Indicator(state = state, isRefreshing = isRefreshing, color = MaterialTheme.colorScheme.MainYellow, containerColor = MaterialTheme.colorScheme.LoadingIndicatorBackground)
                        }
                        MainTopBar(
                            theme = theme,
                            userType = UserType.CLIENT,
                            userName = (authStatus as MainViewState.AuthorizedClient).profile?.userName
                                ?: "",
                            lang = if(clientState.lang is LangResultDomain.Ru) { Lang.RU } else { Lang.ENG },
                            onClickSettings = onclickSettings,
                            onClickLogout = {
                                mainViewModel.logout()
                                onClickLogout()
                                onRefresh()
                            }
                        )
                        EventHorizontalSlider(
                            isAsActive = mainViewModel.asMode,
                            lang = if(clientState.lang is LangResultDomain.Ru) { Lang.RU } else { Lang.ENG },
                            events = Mapper().map((authStatus as MainViewState.AuthorizedClient).events).sortedBy { it.eventId },
                            selectId = eventId,
                            onClickEvent = {
                                mainViewModel.navigateToEvent(
                                    navController = navController,
                                    eventId = eventId.intValue,
                                    eventDestination = eventDestination
                                )
                            },
                        )
                        AchievementsSlider(lang = if(clientState.lang is LangResultDomain.Ru) { Lang.RU } else { Lang.ENG }, achievements = Mapper().map(achievementList = (authStatus as MainViewState.AuthorizedClient).profile?.userAchievements?: listOf()), selectId = selectedAchievement, isShowDialog = isShowAchievementDialog)
                        SubMenu(
                            modifier = Modifier,
                            lang = if(clientState.lang is LangResultDomain.Ru) { Lang.RU } else { Lang.ENG },
                            onClickEvents = onClickEvents,
                            onClickBooks = onClickBooks,
                            onClickStudies = onclickStudies)
                    }

                    clientState.profile?.userAchievements?.find { it.achievementId == selectedAchievement.intValue }?.achievementDescription?.let {
                        GreenDialog(
                            title = if(mainViewModel.getCurrentLang() is LangResultDomain.Ru) { "Ура!" } else { "Congratulations!" },
                            text = it,
                            secondaryActionText = if(mainViewModel.getCurrentLang() is LangResultDomain.Ru){ "Закрыть" } else {"Close"},
                            hideGeneralButton = true,
                            onGeneralActionClick = {
                                isShowAchievementDialog.value = false
                            },
                            onSecondaryActionClick = {
                                isShowAchievementDialog.value = false
                            },
                            isVisible = isShowAchievementDialog
                        )
                    }
                }
                is MainViewState.GuestClient-> {
                    Log.d("user is ", "Guest")
                    val guestState = authStatus as MainViewState.GuestClient
                    val isSettingsDialogGuestVisible  = remember { mutableStateOf(false) }
                    Column(
                        modifier = Modifier
                            .pullToRefresh(
                                state = if(mainViewModel.isConnectionAvailable()) { state } else {
                                    PullToRefreshState()
                                },
                                isRefreshing = isRefreshing,
                                onRefresh = { if(mainViewModel.isConnectionAvailable()){
                                    onRefresh()
                                }
                                }
                            )
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())) {
                        Box(
                            Modifier
                                .align(Alignment.CenterHorizontally)
                                .graphicsLayer {
                                    scaleX = scaleFraction()
                                    scaleY = scaleFraction()
                                }
                                .zIndex(1F)
                        ) {
                            PullToRefreshDefaults.Indicator(state = state, isRefreshing = isRefreshing, color = MaterialTheme.colorScheme.MainYellow, containerColor = MaterialTheme.colorScheme.LoadingIndicatorBackground)
                        }
                        MainTopBar(userType = UserType.GUEST, lang = if(guestState.lang is LangResultDomain.Ru) { Lang.RU } else { Lang.ENG }, onClickSettings = {isSettingsDialogGuestVisible.value = true}, theme = theme, onClickLogout = {
                            mainViewModel.logout()
                            onClickLogout()
                        })
                        EventHorizontalSlider(
                            isAsActive = mainViewModel.asMode,
                            lang = if(guestState.lang is LangResultDomain.Ru) { Lang.RU } else { Lang.ENG }, events = Mapper().map((authStatus as MainViewState.GuestClient).events),
                            selectId = eventId,
                            onClickEvent = {
                                mainViewModel.navigateToEvent(
                                    navController = navController,
                                    eventId = eventId.intValue,
                                    eventDestination = eventDestination
                                )
                            }
                        )

                        AchievementsSlider(lang = if(guestState.lang is LangResultDomain.Ru) { Lang.RU } else {Lang.ENG}, achievements = listOf(), selectId = selectedAchievement, isShowDialog = isShowAchievementDialog)
                        SubMenu(
                            modifier = Modifier,
                            lang = if(guestState.lang is LangResultDomain.Ru) { Lang.RU } else {Lang.ENG},
                            onClickEvents = onClickEvents,
                            onClickBooks = onClickBooks,
                            onClickStudies = onclickStudies)
                    }
                    GreenDialog(
                        title = mainViewModel.getSettingsGuestDialogTitle(),
                        text = mainViewModel.getSettingsGuestDialogText(),
                        generalActionText = mainViewModel.getDialogOkBtn(),
                        hideSecondaryButton = true,
                        onGeneralActionClick = {
                            isSettingsDialogGuestVisible.value = false
                        },
                        onSecondaryActionClick = {
                            isSettingsDialogGuestVisible.value = false
                        },
                        isVisible = isSettingsDialogGuestVisible
                    )
                    GreenDialog(
                        title = mainViewModel.getGuestNotificationDialogTitle(),
                        text = mainViewModel.getGuestNotificationDialogText(),
                        generalActionText = mainViewModel.getGuestNotificationDialogActionBtn(),
                        secondaryActionText = mainViewModel.getGuestNotificationDialogSecondBtn(),
                        onGeneralActionClick = {
                            isShowGuestNotificationDialog.value = false
                            mainViewModel.logout()
                            onClickLogout()
                        },
                        onSecondaryActionClick = {
                            isShowGuestNotificationDialog.value = false
                        },
                        isVisible = isShowGuestNotificationDialog
                    )
                }

                is MainViewState.Loading-> {
                    Log.d("user is ", "Loading")
                    val loadingState = authStatus as MainViewState.Loading
                    LoadingScreen(
                        lang = if(loadingState.lang is LangResultDomain.Ru){ Lang.RU } else { Lang.ENG },
                        theme = theme,
                        userType = UserType.UNKNOWN,
                        onClickEvents = onClickEvents,
                        onClickBooks = onClickBooks,
                        onclickStudies = onclickStudies,
                        userName = ""
                    )
                    mainViewModel.initViewModel()
                }

                is MainViewState.ErrorState-> {
                Log.d("User is ", "Error")
                }
            }
        }

        ProgressState.LOADING ->{
            Log.d("realy complete", "no")
            LoadingScreen(
                lang = if(mainViewModel.getCurrentLang() is LangResultDomain.Ru) { Lang.RU } else { Lang.ENG },
                theme = theme,
                userName = "",
                userType = UserType.UNKNOWN,
                onClickEvents = onClickEvents,
                onClickBooks = onClickBooks) {

            }
            mainViewModel.initViewModel()
        }
    }
}

@Composable
fun LoadingScreen(lang: Lang, theme: String, userName: String, userType: UserType, onClickEvents: () -> Unit, onClickBooks: () -> Unit, onclickStudies: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
            .verticalScroll(rememberScrollState())
    ) {
        MainTopBar(
            theme = theme,
            userName = userName,
            userType = userType,
            lang = lang,
            onClickLogout = {},
            onClickSettings = {}
        )
        Title(modifier = Modifier, text = if(lang==Lang.RU){ "Ближайшие мероприятия" } else{ "Events" })

        Row(modifier = Modifier
            .fillMaxWidth()) {
            repeat(3){
                Column(modifier = Modifier
                    .width(320.dp)
                    .padding(horizontal = 20.dp)) {
                    ShimmerEffect(
                        modifier = Modifier
                            .width(280.dp)
                            .height(180.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(5)),
                        durationMillis = 1000
                    )
                    Row(modifier = Modifier.padding(top = 8.dp)) {
                        ShimmerEffect(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .width(118.dp)
                                .height(20.dp)
                                .background(Color.LightGray, shape = RoundedCornerShape(5)),
                            durationMillis = 1000
                        )
                        Spacer(modifier = Modifier
                            .weight(1F))
                        ShimmerEffect(
                            modifier = Modifier
                                .width(80.dp)
                                .height(20.dp)
                                .background(Color.LightGray, shape = RoundedCornerShape(5)),
                            durationMillis = 1000
                        )
                    }
                    Row(modifier = Modifier.padding(top = 8.dp)) {
                        ShimmerEffect(
                            modifier = Modifier
                                .width(140.dp)
                                .height(20.dp)
                                .background(Color.LightGray, shape = RoundedCornerShape(5)),
                            durationMillis = 1000
                        )
                        Spacer(modifier = Modifier
                            .weight(1F))
                        ShimmerEffect(
                            modifier = Modifier
                                .width(118.dp)
                                .height(20.dp)
                                .background(Color.LightGray, shape = RoundedCornerShape(5)),
                            durationMillis = 1000
                        )
                    }
                }

            }
        }
        Title(modifier = Modifier
            .padding(top = 48.dp),
            text = if(lang==Lang.RU){ "Твои достижения" } else{ "Your achievements" })
        Row(modifier = Modifier
            .fillMaxWidth()) {
            repeat(5){
                ShimmerEffect(
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .width(160.dp)
                        .height(140.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(5)),
                    durationMillis = 1000
                )
            }
        }

        SubMenu(
            modifier = Modifier.padding(top = 48.dp),
            lang = lang,
            onClickEvents = onClickEvents,
            onClickBooks = onClickBooks,
            onClickStudies = onclickStudies)
    }
}
