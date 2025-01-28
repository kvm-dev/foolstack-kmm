package ru.foolstack.events.impl.presentation.ui

import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.foolstack.events.impl.mapper.Mapper
import ru.foolstack.events.impl.presentation.viewmodel.EventsViewModel
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.model.ProgressState
import ru.foolstack.ui.components.EventVerticalSlider
import ru.foolstack.ui.components.NotificationTitle
import ru.foolstack.ui.components.ShimmerEffect
import ru.foolstack.ui.components.Title
import ru.foolstack.ui.components.TopBar
import ru.foolstack.ui.components.YellowButton
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.R.drawable

@Composable
fun EventsScreen(
    eventsViewModel: EventsViewModel = koinViewModel(),
    navController: NavController,
    eventDestination: String) {
    val eventId  = remember { mutableIntStateOf(0) }
    val selectedFilter  = remember { mutableStateOf("") }
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val backDispatcher =
        checkNotNull(LocalOnBackPressedDispatcherOwner.current).onBackPressedDispatcher
    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            // fetch something
            delay(2000)
            eventsViewModel.refresh()
            isRefreshing = false
        }
    }

    val viewModelState by eventsViewModel.progressState.collectAsState()
    when (viewModelState) {
        ProgressState.COMPLETED -> {
            Log.d("realy event Complete", "yes")
            val eventsState by eventsViewModel.uiState.collectAsState()
            when (eventsState) {
                is EventsViewState.LoadingState -> {
                    Log.d("event in state is", "Loading")
                    LoadingScreen(lang = eventsViewModel.getCurrentLang(), onBackClick = { backDispatcher.onBackPressed() })
                }

                is EventsViewState.ErrorState -> {
                    Log.d("event in state is", "Error")
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(top = 40.dp)
                    ) {
                        TopBar(
                            screenTitle = if ((eventsState as EventsViewState.ErrorState).lang is LangResultDomain.Ru) {
                                "События"
                            } else {
                                "Events"
                            }, action = { backDispatcher.onBackPressed() })
                        Column(modifier = Modifier.align(Alignment.Center)) {
                            val bugBitmap = ImageBitmap.imageResource(id = drawable. bug_icon)
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(100.dp, 10.dp),
                                bitmap = bugBitmap,
                                contentDescription = "FoolStack"
                            )
                            Title(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = if ((eventsState as EventsViewState.ErrorState).lang is LangResultDomain.Ru) {
                                    "Что-то пошло не так..."
                                } else {
                                    "Something went wrong..."
                                }
                            )
                            YellowButton(
                                onClick = { eventsViewModel.refresh() },
                                text = if ((eventsState as EventsViewState.ErrorState).lang is LangResultDomain.Ru) {
                                    "Обновить"
                                } else {
                                    "Refresh"
                                },
                                isEnabled = true,
                                isLoading = false,
                                modifier = Modifier
                                    .padding(top = 30.dp, start = 16.dp, end = 16.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }

                is EventsViewState.SuccessState -> {
                    Log.d("event in state is", "Success")
                    val successState = (eventsState as EventsViewState.SuccessState)
                    if (successState.events?.events?.isNotEmpty() == true) {

                        EventVerticalSlider(
                            lang = if (successState.lang is LangResultDomain.Ru) {
                                Lang.RU
                            } else {
                                Lang.ENG
                            },
                            events = Mapper().mapToEventItems(successState.events),
                            chips = Mapper().mapToChips(
                                successState.events.events
                            ),
                            selectedChips = successState.selectedFilters,
                            onBackPressed = { backDispatcher.onBackPressed() },
                            onRefresh = {
                                onRefresh()
                            },
                            isRefreshing = isRefreshing,
                            selectId = eventId,
                            onClickEvent = {
                                eventsViewModel.navigateToEvent(
                                    navController = navController,
                                    eventId = eventId.intValue,
                                    eventDestination = eventDestination
                                )
                            },
                            selectedChip = selectedFilter,
                            onclickChip = {eventsViewModel.updateFilters(selectedFilter.value)}
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .fillMaxSize()
                                .padding(top = 40.dp)
                        ) {

                            TopBar(
                                screenTitle = if (successState.lang is LangResultDomain.Ru) {
                                    "События"
                                } else {
                                    "Events"
                                }, action = { backDispatcher.onBackPressed() })
                            val emptyText = if (successState.lang is LangResultDomain.Ru) {
                                "В ближайшее время мероприятий\nне планируется"
                            } else {
                                "There are not events planned\nin the near future"
                            }
                            Column(modifier = Modifier.align(Alignment.Center)) {
                                NotificationTitle(
                                    text = emptyText,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                                YellowButton(
                                    onClick = { onRefresh() },
                                    text = if (successState.lang is LangResultDomain.Ru) {
                                        "Обновить"
                                    } else {
                                        "Refresh"
                                    },
                                    isEnabled = true,
                                    isLoading = false,
                                    modifier = Modifier
                                        .padding(top = 30.dp, start = 16.dp, end = 16.dp)
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }

        ProgressState.LOADING -> {
            LoadingScreen(lang = eventsViewModel.getCurrentLang(), onBackClick = { backDispatcher.onBackPressed() })
            Log.d("event realy loading", "yes")
            eventsViewModel.initViewModel()
        }

        else -> {
            Log.d("event realy complete", "no")
            eventsViewModel.initViewModel()
        }
    }
}

@Composable
fun LoadingScreen(lang: LangResultDomain, onBackClick: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
    ) {
        TopBar(
            screenTitle = if (lang is LangResultDomain.Ru) {
                "События"
            } else {
                "Events"
            }, action = { onBackClick() })
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(start = 20.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            repeat(6) {
                ShimmerEffect(
                    modifier = Modifier
                        .size(width = 120.dp, height = 38.dp)
                        .padding(horizontal = 2.dp)
                        .background(Color.LightGray, RoundedCornerShape(34)),
                    durationMillis = 1000
                )
            }
        }
        repeat(10) {
            Column {
                ShimmerEffect(
                    modifier = Modifier
                        .height(220.dp)
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 12.dp)
                        .background(Color.LightGray, RoundedCornerShape(10)),
                    durationMillis = 1000
                )
                Row {
                    ShimmerEffect(
                        modifier = Modifier
                            .height(36.dp)
                            .width(120.dp)
                            .padding(start = 20.dp, end = 20.dp, top = 12.dp)
                            .background(Color.LightGray, RoundedCornerShape(10)),
                        durationMillis = 1000
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ShimmerEffect(
                        modifier = Modifier
                            .height(34.dp)
                            .width(100.dp)
                            .padding(start = 20.dp, end = 20.dp, top = 8.dp)
                            .background(Color.LightGray, RoundedCornerShape(30)),
                        durationMillis = 1000
                    )
                }
                Row {
                    ShimmerEffect(
                        modifier = Modifier
                            .height(34.dp)
                            .width(200.dp)
                            .padding(start = 20.dp, end = 20.dp, top = 8.dp)
                            .background(Color.LightGray, RoundedCornerShape(10)),
                        durationMillis = 1000
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ShimmerEffect(
                        modifier = Modifier
                            .height(32.dp)
                            .width(100.dp)
                            .padding(start = 20.dp, end = 20.dp, top = 8.dp)
                            .background(Color.LightGray, RoundedCornerShape(30)),
                        durationMillis = 1000
                    )
                }
            }
        }
    }
}