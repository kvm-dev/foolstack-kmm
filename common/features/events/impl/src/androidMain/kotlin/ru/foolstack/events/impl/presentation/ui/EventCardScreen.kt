package ru.foolstack.events.impl.presentation.ui

import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.foolstack.events.impl.presentation.viewmodel.EventCardViewModel
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.model.ProgressState
import ru.foolstack.ui.R
import ru.foolstack.ui.components.CardBigDescription
import ru.foolstack.ui.components.CardText
import ru.foolstack.ui.components.CardTitle
import ru.foolstack.ui.components.GreenButton
import ru.foolstack.ui.components.ServiceSubLabel
import ru.foolstack.ui.components.ServiceTitleText
import ru.foolstack.ui.components.Title
import ru.foolstack.ui.components.TopBar
import ru.foolstack.ui.components.YellowButton
import ru.foolstack.ui.utils.decodeBase64ToBitmap
import ru.foolstack.ui.utils.timestampToDateString


@Composable
fun EventCardScreen(eventCardViewModel: EventCardViewModel = koinViewModel(), eventId: Int) {
    val backDispatcher =
        checkNotNull(LocalOnBackPressedDispatcherOwner.current).onBackPressedDispatcher
    val viewModelState by eventCardViewModel.progressState.collectAsState()
    when (viewModelState) {
        ProgressState.COMPLETED -> {
            Log.d("realy eventCard Complete", "yes")
            val eventState by eventCardViewModel.uiState.collectAsState()
            when (eventState) {

                is EventCardViewState.Idle -> {
                    Log.d("event in state is", "Error")
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(top = 40.dp)
                    ) {
                        TopBar(
                            isDark = false,
                            screenTitle = if ((eventState as EventCardViewState.Idle).lang is LangResultDomain.Ru) {
                                "Событие"
                            } else {
                                "Event"
                            }, action = { backDispatcher.onBackPressed() })
                        Column(modifier = Modifier.align(Alignment.Center)) {
                            val bugBitmap = ImageBitmap.imageResource(id = R.drawable.bug_icon)
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(100.dp, 10.dp),
                                bitmap = bugBitmap,
                                contentDescription = "FoolStack"
                            )
                            Title(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = if ((eventState as EventCardViewState.Idle).lang is LangResultDomain.Ru) {
                                    "Что-то пошло не так..."
                                } else {
                                    "Something went wrong..."
                                }
                            )
                            YellowButton(
                                onClick = { backDispatcher.onBackPressed() },
                                text = if ((eventState as EventCardViewState.Idle).lang is LangResultDomain.Ru) {
                                    "Вернуться к событиям"
                                } else {
                                    "Return to events"
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

                is EventCardViewState.SuccessState -> {
                    Log.d("eventCard in state is", "Success")
                    val successState = (eventState as EventCardViewState.SuccessState)
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        Column(modifier = Modifier.align(Alignment.TopCenter)) {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp)
                                .wrapContentHeight()) {
                                successState.event?.eventImageBase64?.decodeBase64ToBitmap()?.let {
                                    Image(
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize().
                                        height(320.dp),
                                        bitmap = it,
                                        contentDescription = successState.event.eventName
                                    )
                                    TopBar(
                                        screenTitle = successState.event.eventName ?: "",
                                        action = { backDispatcher.onBackPressed() },
                                        isDark = false,
                                        isTitleVisible = false,
                                    )
                                    Column(modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .fillMaxWidth()
                                        .padding(top = 150.dp)) {
                                        Row(modifier = Modifier.padding(start = 6.dp)) {
                                            var cost = ""
                                            var symbol = ""
                                            symbol = if (successState.lang is LangResultDomain.Ru) {
                                                "₽"
                                            } else {
                                                "$"
                                            }
                                            cost = if (successState.event.eventCost > 0) {
                                                "${successState.event.eventCost} $symbol"
                                            } else {
                                                if (successState.lang is LangResultDomain.Ru) {
                                                    "бесплатно"
                                                } else {
                                                    "free"
                                                }
                                            }
                                            ServiceSubLabel(
                                                text = successState.event.eventDateStart.timestampToDateString(),
                                                modifier = Modifier
                                            )
                                            ServiceTitleText(
                                                modifier = Modifier
                                                    .padding(start = 4.dp)
                                                    .align(Alignment.CenterVertically), text = cost
                                            )
                                        }
                                        Box(
                                            modifier = Modifier
                                                .padding(top = 24.dp)
                                                .fillMaxWidth()
                                                .clip(
                                                    shape = RoundedCornerShape(
                                                        15.dp,
                                                        15.dp,
                                                        0.dp,
                                                        0.dp
                                                    )
                                                )
                                                .background(MaterialTheme.colorScheme.background)
                                                .align(Alignment.CenterHorizontally)
                                        ) {
                                            CardTitle(
                                                text = successState.event.eventName, modifier = Modifier
                                                    .wrapContentHeight()
                                                    .padding(
                                                        top = 24.dp,
                                                        bottom = 34.dp,
                                                        start = 24.dp,
                                                        end = 24.dp
                                                    )
                                                    .align(Alignment.Center)
                                            )
                                        }
                                    }
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                CardBigDescription(
                                    text = if (successState.lang is LangResultDomain.Ru) {
                                        "Подробности"
                                    } else {
                                        "Details"
                                    },
                                    modifier = Modifier
                                        .align(Alignment.Start)
                                        .padding(horizontal = 20.dp)
                                )
                                CardText(
                                    text = successState.event?.eventDescription ?: "",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                                )

                                GreenButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 64.dp, bottom = 34.dp, start = 20.dp, end = 20.dp),
                                    text = if (successState.lang is LangResultDomain.Ru) {
                                        "Стать участником"
                                    } else {
                                        "Join event"
                                    },
                                    isEnabled = true,
                                    isLoading = false,
                                    onClick = { eventCardViewModel.onClickJoinEvent(url = successState.event?.eventRefLink?:"")},
                                )
                            }
                        }
                    }
                }
            }
        }

        else -> {
            Log.d("eventCard realy complete", "no")
            eventCardViewModel.initViewModel(eventId = eventId)
        }
    }
}