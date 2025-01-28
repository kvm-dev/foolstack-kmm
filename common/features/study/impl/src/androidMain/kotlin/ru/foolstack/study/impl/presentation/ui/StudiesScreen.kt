package ru.foolstack.study.impl.presentation.ui

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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.model.ProgressState
import ru.foolstack.study.impl.mapper.Mapper
import ru.foolstack.study.impl.presentation.viewmodel.StudiesViewModel
import ru.foolstack.ui.R
import ru.foolstack.ui.components.NotificationTitle
import ru.foolstack.ui.components.ShimmerEffect
import ru.foolstack.ui.components.StudiesVerticalSlider
import ru.foolstack.ui.components.Title
import ru.foolstack.ui.components.TopBar
import ru.foolstack.ui.components.YellowButton
import ru.foolstack.ui.model.Lang

@Composable
fun StudiesScreen(studiesViewModel: StudiesViewModel = koinViewModel()) {
    val studyId  = remember { mutableIntStateOf(0) }
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
            studiesViewModel.refresh()
            isRefreshing = false
        }
    }

    val viewModelState by studiesViewModel.progressState.collectAsState()
    when (viewModelState) {
        ProgressState.COMPLETED -> {
            Log.d("realy studies Complete", "yes")
            val studiesState by studiesViewModel.uiState.collectAsState()
            when (studiesState) {
                is StudiesViewState.LoadingState -> {
                    Log.d("studies in state is", "Loading")
                    LoadingScreen(lang = studiesViewModel.getCurrentLang(), onBackClick = { backDispatcher.onBackPressed() })
                }

                is StudiesViewState.ErrorState -> {
                    Log.d("studies in state is", "Error")
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(top = 40.dp)
                    ) {
                        TopBar(
                            screenTitle = if ((studiesState as StudiesViewState.ErrorState).lang is LangResultDomain.Ru) {
                                "Обучение"
                            } else {
                                "Education"
                            }, action = { backDispatcher.onBackPressed() })
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
                                text = if ((studiesState as StudiesViewState.ErrorState).lang is LangResultDomain.Ru) {
                                    "Что-то пошло не так..."
                                } else {
                                    "Something went wrong..."
                                }
                            )
                            YellowButton(
                                onClick = { studiesViewModel.refresh() },
                                text = if ((studiesState as StudiesViewState.ErrorState).lang is LangResultDomain.Ru) {
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

                is StudiesViewState.SuccessState -> {
                    Log.d("studies in state is", "Success")
                    val successState = (studiesState as StudiesViewState.SuccessState)
                    if (successState.studies?.studies?.isNotEmpty() == true) {

                        StudiesVerticalSlider(
                            lang = if(successState.lang is LangResultDomain.Ru){Lang.RU}else{Lang.ENG},
                            studies = Mapper().mapToStudiesItems(successState.studies.studies),
                            prText = successState.studies.prText,
                            chips = Mapper().mapToChips(
                                successState.studies.studies
                            ),
                            selectedChips = successState.selectedFilters,
                            onBackPressed = { backDispatcher.onBackPressed() },
                            onRefresh = { onRefresh() },
                            isRefreshing = isRefreshing,
                            onClickStudy = { successState.studies.studies.find { it.studyId == studyId.intValue }?.studyRefLink?.let {
                                studiesViewModel.onStudyClick(
                                    it
                                )
                            } },
                            selectId = studyId,
                            selectedChip = selectedFilter,
                            onclickChip = { studiesViewModel.updateFilters(selectedFilter.value) }
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
                                    "Обучение"
                                } else {
                                    "Education"
                                }, action = { backDispatcher.onBackPressed() })
                            val emptyText = if (successState.lang is LangResultDomain.Ru) {
                                "На данный момент нет\nобразовательных программ"
                            } else {
                                "There are currently\nno educational programs."
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
            LoadingScreen(lang = studiesViewModel.getCurrentLang(), onBackClick = { backDispatcher.onBackPressed() })
            Log.d("studies realy loading", "yes")
            studiesViewModel.initViewModel()
        }

        else -> {
            Log.d("studies realy complete", "no")
            studiesViewModel.initViewModel()
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
                "Обучение"
            } else {
                "Education"
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