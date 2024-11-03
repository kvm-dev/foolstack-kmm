package ru.foolstack.events.impl.presentation.ui

import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
fun EventsScreen(eventsViewModel: EventsViewModel = koinViewModel()) {

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
            val eventState by eventsViewModel.uiState.collectAsState()
            when (eventState) {
                is EventsViewState.LoadingState -> {
                    Log.d("event in state is", "Loading")
                    Column( modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp)){
                        TopBar(
                            screenTitle = if ((eventState as EventsViewState.LoadingState).lang is LangResultDomain.Ru) {
                                "События"
                            } else {
                                "Events"
                            }, onBackPressed = { backDispatcher.onBackPressed() })
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
                                ShimmerEffect(
                                    modifier = Modifier
                                        .height(220.dp)
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp, vertical = 18.dp)
                                        .background(Color.LightGray, RoundedCornerShape(10)),
                                    durationMillis = 1000
                                )
                            }
                    }
                }

                is EventsViewState.ErrorState -> {
                    Log.d("event in state is", "Error")
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(top = 40.dp)) {
                        TopBar(
                            screenTitle = if ((eventState as EventsViewState.ErrorState).lang is LangResultDomain.Ru) {
                                "События"
                            } else {
                                "Events"
                            }, onBackPressed = { backDispatcher.onBackPressed() })
                        Column(modifier = Modifier.align(Alignment.Center)) {
                            val bugBitmap = ImageBitmap.imageResource(id = drawable.bug_icon)
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(100.dp, 10.dp),
                                bitmap = bugBitmap,
                                contentDescription = "FoolStack"
                            )
                            Title(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = if((eventState as EventsViewState.ErrorState).lang is LangResultDomain.Ru) {
                                    "Что-то пошло не так..."
                                } else {
                                    "Something went wrong..."
                                })
                            YellowButton(onClick = { eventsViewModel.refresh() }, text = if ((eventState as EventsViewState.ErrorState).lang is LangResultDomain.Ru) {
                                "Обновить"
                            } else {
                                "Refresh"
                            }, isEnabled = true, isLoading = false, modifier = Modifier
                                .padding(top = 30.dp))
                        }
                    }
                }

                is EventsViewState.SuccessState -> {
                    Log.d("event in state is", "Success")
                    val successState = (eventState as EventsViewState.SuccessState)
                    if(successState.events?.events?.isNotEmpty() == true){
                        val filteredSubsList = HashSet<String>()
                        val selectedList = remember { mutableStateListOf("") }
                        successState.events.events.forEach { event ->
                            event.eventSubs.forEach { sub ->
                                filteredSubsList.add(sub.subName)
                            }
                        }
                        //init Chips
                        filteredSubsList.forEach { sub ->
                            selectedList.add(sub)
                        }
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
                            selectedChips = selectedList,
                            onBackPressed = { backDispatcher.onBackPressed()},
                            onRefresh = {onRefresh()},
                            isRefreshing = isRefreshing
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .fillMaxSize()
                                .padding(top = 40.dp)) {

                            TopBar(
                                screenTitle = if (successState.lang is LangResultDomain.Ru) {
                                    "События"
                                } else {
                                    "Events"
                                }, onBackPressed = { backDispatcher.onBackPressed() })
                            val emptyText = if (successState.lang is LangResultDomain.Ru) {
                                "В ближайшее время мероприятий\nне планируется"
                            } else {
                                "There are not events planned\nin the near future"
                            }
                            Column(modifier = Modifier.align(Alignment.Center)) {
                                NotificationTitle(
                                    text = emptyText,
                                    modifier = Modifier.align(Alignment.CenterHorizontally))
                                YellowButton(onClick = { eventsViewModel.refresh() }, text = if (successState.lang is LangResultDomain.Ru) {
                                    "Обновить"
                                } else {
                                    "Refresh"
                                }, isEnabled = true, isLoading = false, modifier = Modifier
                                    .padding(top = 30.dp))
                            }
                        }
                    }
                }
            }
        }

        else -> {
            Log.d("event realy complete", "no")
            eventsViewModel.initViewModel()
        }
    }
}

