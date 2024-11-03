package ru.foolstack.main.impl.presentation.ui

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
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
import ru.foolstack.profile.api.model.ProfileDomain
import ru.foolstack.ui.components.AchievementsSlider
import ru.foolstack.ui.components.EventHorizontalSlider
import ru.foolstack.ui.components.MainTopBar
import ru.foolstack.ui.components.UserType
import ru.foolstack.ui.model.AchievementItem
import ru.foolstack.ui.model.Lang
import ru.foolstack.utils.model.ResultState
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.foolstack.ui.components.SubMenu
import ru.foolstack.ui.theme.LoadingIndicatorBackground
import ru.foolstack.ui.theme.MainYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainViewModel: MainViewModel = koinViewModel(),
               onClickEvents: () -> Unit = {},) {
    var isRefreshing by remember { mutableStateOf(false) }
    val state = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()

    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            // fetch something
            delay(5000)
            isRefreshing = false
        }
    }
    val scaleFraction = {
        if (isRefreshing) 1f
        else LinearOutSlowInEasing.transform(state.distanceFraction).coerceIn(0f, 1f)
    }

    val viewModelState by mainViewModel.progressState.collectAsState()
    when(viewModelState) {
        ProgressState.COMPLETED -> {
            Log.d("realy Complete", "yes")
            val profileState by mainViewModel.profileState.collectAsState()
            when(profileState){
                is ResultState.Idle-> {
                    Log.d("state is", "idle")
                }
                is ResultState.Loading-> {
                    Log.d("state is", "Loading")
                }
                is ResultState.Success->{
                    val authStatus by mainViewModel.uiState.collectAsState()
                    when(authStatus){
                      is MainViewState.AuthorizedClient-> {
                          Column(
                              modifier = Modifier
                                  .pullToRefresh(
                                      state = state,
                                      isRefreshing = isRefreshing,
                                      onRefresh = onRefresh
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
                                  userType = UserType.CLIENT,
                                  userName = (authStatus as MainViewState.AuthorizedClient).profile?.userName
                                      ?: "",
                                  lang = Lang.RU
                              )
                              EventHorizontalSlider(
                                  lang = Lang.RU,
                                  events = Mapper().map((authStatus as MainViewState.AuthorizedClient).events)
                              )

                              val testList = ArrayList<AchievementItem>()
                              testList.add(
                                  AchievementItem(
                                      achievementId = 1,
                                      achievementLevel = 1,
                                      achievementName = "Фулстакер",
                                      achievementDescription = "description"
                                  )
                              )
                              testList.add(
                                  AchievementItem(
                                      achievementId = 2,
                                      achievementLevel = 2,
                                      achievementName = "Собеседник",
                                      achievementDescription = "description"
                                  )
                              )
                              testList.add(
                                  AchievementItem(
                                      achievementId = 3,
                                      achievementLevel = 3,
                                      achievementName = "Комментатор",
                                      achievementDescription = "description"
                                  )
                              )
                              testList.add(
                                  AchievementItem(
                                      achievementId = 4,
                                      achievementLevel = 2,
                                      achievementName = "Ментор",
                                      achievementDescription = "description"
                                  )
                              )
                              testList.add(
                                  AchievementItem(
                                      achievementId = 5,
                                      achievementLevel = 0,
                                      achievementName = "Читатель",
                                      achievementDescription = "description"
                                  )
                              )
                              testList.add(
                                  AchievementItem(
                                      achievementId = 6,
                                      achievementLevel = 2,
                                      achievementName = "Достигатор",
                                      achievementDescription = "description"
                                  )
                              )
                              testList.add(
                                  AchievementItem(
                                      achievementId = 7,
                                      achievementLevel = 0,
                                      achievementName = "Генератор идей",
                                      achievementDescription = "description"
                                  )
                              )
                              testList.add(
                                  AchievementItem(
                                      achievementId = 8,
                                      achievementLevel = 3,
                                      achievementName = "Вечный студент",
                                      achievementDescription = "description"
                                  )
                              )
//                              AchievementsSlider(lang = Lang.RU, achievements = Mapper().map(achievementList = (authStatus as MainViewState.AuthorizedClient).profile?.userAchievements?: listOf()))
                              AchievementsSlider(lang = Lang.RU, achievements = testList)
                              SubMenu(lang = Lang.RU, onClickEvents = onClickEvents)
                          }
                      }
                      is MainViewState.GuestClient-> {
                          Column {
                              MainTopBar(userType = UserType.CLIENT, lang = Lang.RU)
                              EventHorizontalSlider(lang = Lang.RU, events = Mapper().map((authStatus as MainViewState.GuestClient).events))
                          }
                      }

                      is MainViewState.Loading-> {

                      }

                      is MainViewState.ErrorState-> {

                      }
                    }
                    Log.d("state is", "success, userName is ${(profileState as ResultState.Success<ProfileDomain>).data?.userName}")
                }
            }
        }
        else->{
            Log.d("realy complete", "no")
            mainViewModel.initViewModel()

        }
    }
}
