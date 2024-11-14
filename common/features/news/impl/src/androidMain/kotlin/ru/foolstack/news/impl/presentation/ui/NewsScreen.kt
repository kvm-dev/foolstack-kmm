package ru.foolstack.news.impl.presentation.ui

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
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.model.ProgressState
import ru.foolstack.news.impl.mapper.Mapper
import ru.foolstack.news.impl.presentation.viewmodel.NewsViewModel
import ru.foolstack.ui.R
import ru.foolstack.ui.components.EventVerticalSlider
import ru.foolstack.ui.components.NewsVerticalSlider
import ru.foolstack.ui.components.NotificationTitle
import ru.foolstack.ui.components.ShimmerEffect
import ru.foolstack.ui.components.Title
import ru.foolstack.ui.components.TopBar
import ru.foolstack.ui.components.YellowButton
import ru.foolstack.ui.model.Lang

@Composable
fun NewsScreen(newsViewModel: NewsViewModel = koinViewModel(), navController: NavController, newsDestination: String) {
    val newsId  = remember { mutableIntStateOf(0) }
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val backDispatcher =
        checkNotNull(LocalOnBackPressedDispatcherOwner.current).onBackPressedDispatcher
    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            // fetch something
            delay(2000)
            newsViewModel.refresh()
            isRefreshing = false
        }
    }

    val viewModelState by newsViewModel.progressState.collectAsState()
    when (viewModelState) {
        ProgressState.COMPLETED -> {
            Log.d("realy news Complete", "yes")
            val newsState by newsViewModel.uiState.collectAsState()
            when (newsState) {
                is NewsViewState.LoadingState -> {
                    Log.d("news in state is", "Loading")
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 40.dp)
                    ) {
                        TopBar(
                            screenTitle = if ((newsState as NewsViewState.LoadingState).lang is LangResultDomain.Ru) {
                                "Новости"
                            } else {
                                "News"
                            }, onBackPressed = { backDispatcher.onBackPressed() })

                        repeat(10) {
                            Column(modifier = Modifier
                                .padding(vertical = 20.dp)) {
                                ShimmerEffect(
                                    modifier = Modifier
                                        .height(220.dp)
                                        .fillMaxWidth()
                                        .padding(start = 20.dp, end = 20.dp, top = 12.dp)
                                        .background(Color.LightGray, RoundedCornerShape(10)),
                                    durationMillis = 1000
                                )

                                ShimmerEffect(
                                    modifier = Modifier
                                        .height(44.dp)
                                        .fillMaxWidth()
                                        .padding(start = 20.dp, end = 80.dp, top = 4.dp)
                                        .background(Color.LightGray, RoundedCornerShape(20)),
                                    durationMillis = 1000
                                )

                                ShimmerEffect(
                                    modifier = Modifier
                                        .height(36.dp)
                                        .width(120.dp)
                                        .padding(start = 20.dp, end = 20.dp, top = 16.dp)
                                        .background(Color.LightGray, RoundedCornerShape(10)),
                                    durationMillis = 1000
                                )

                                ShimmerEffect(
                                    modifier = Modifier
                                        .height(80.dp)
                                        .fillMaxWidth()
                                        .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                                        .background(Color.LightGray, RoundedCornerShape(10)),
                                    durationMillis = 1000
                                )
                            }
                        }
                    }
                }

                is NewsViewState.ErrorState -> {
                    Log.d("news in state is", "Error")
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(top = 40.dp)
                    ) {
                        TopBar(
                            screenTitle = if ((newsState as NewsViewState.ErrorState).lang is LangResultDomain.Ru) {
                                "Новости"
                            } else {
                                "News"
                            }, onBackPressed = { backDispatcher.onBackPressed() })
                        Column(modifier = Modifier.align(Alignment.Center)) {
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
                                text = if ((newsState as NewsViewState.ErrorState).lang is LangResultDomain.Ru) {
                                    "Что-то пошло не так..."
                                } else {
                                    "Something went wrong..."
                                }
                            )
                            YellowButton(
                                onClick = { newsViewModel.refresh() },
                                text = if ((newsState as NewsViewState.ErrorState).lang is LangResultDomain.Ru) {
                                    "Обновить"
                                } else {
                                    "Refresh"
                                },
                                isEnabled = true,
                                isLoading = false,
                                modifier = Modifier
                                    .padding(top = 30.dp)
                            )
                        }
                    }
                }

                is NewsViewState.SuccessState -> {
                    Log.d("news in state is", "Success")
                    val successState = (newsState as NewsViewState.SuccessState)
                    if (successState.news?.news?.isNotEmpty() == true) {

                        NewsVerticalSlider(
                            lang = if(successState.lang is LangResultDomain.Ru){ Lang.RU } else {Lang.ENG},
                            news = Mapper().mapToNewsItems(successState.news),
                            onBackPressed = {  },
                            onRefresh = { onRefresh() },
                            isRefreshing = false,
                            onClickNews = { newsViewModel.navigateToSingleNews(
                                navController = navController,
                                newsId = newsId.intValue,
                                newsDestination = newsDestination) },
                            selectId = newsId
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
                                    "Новости"
                                } else {
                                    "News"
                                }, onBackPressed = { backDispatcher.onBackPressed() })
                            val emptyText = if (successState.lang is LangResultDomain.Ru) {
                                "К сожалению актуальных новостей\nсейчас нет"
                            } else {
                                "Unfortunately, there is no news \nat the moment"
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
                                        .padding(top = 30.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        else -> {
            Log.d("news realy complete", "no")
            newsViewModel.initViewModel()
        }
    }
}