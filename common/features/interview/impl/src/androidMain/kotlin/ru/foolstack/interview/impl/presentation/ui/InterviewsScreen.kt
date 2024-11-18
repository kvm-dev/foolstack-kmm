package ru.foolstack.interview.impl.presentation.ui

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
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.foolstack.interview.api.model.MaterialDomain
import ru.foolstack.interview.api.model.SubProfessionDomain
import ru.foolstack.interview.impl.presentation.viewmodel.InterviewsViewModel
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.model.ProgressState
import ru.foolstack.ui.R
import ru.foolstack.ui.components.MaterialsExpandableList
import ru.foolstack.ui.components.ShimmerEffect
import ru.foolstack.ui.components.Title
import ru.foolstack.ui.components.TopBar
import ru.foolstack.ui.components.YellowButton
import ru.foolstack.interview.impl.mapper.Mapper

@Composable
fun InterviewsScreen(
    interviewsViewModel: InterviewsViewModel = koinViewModel(),
    navController: NavController,
    interviewsDestination: String,
    notFoundProfession: () -> Unit) {
    val materialId  = remember { mutableIntStateOf(0) }
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
            interviewsViewModel.refresh()
            isRefreshing = false
        }
    }

    LifecycleResumeEffect(Unit) {
        interviewsViewModel.initViewModel()

        onPauseOrDispose {
        }
    }

    val viewModelState by interviewsViewModel.progressState.collectAsState()
    when (viewModelState) {
        ProgressState.COMPLETED -> {
            Log.d("realy interviews Complete", "yes")
            val interviewsState by interviewsViewModel.uiState.collectAsState()
            when (interviewsState) {
                is InterviewsViewState.LoadingState -> {
                    Log.d("interviews in state is", "Loading")
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 40.dp)
                    ) {
                        TopBar(
                            screenTitle = if ((interviewsState as InterviewsViewState.LoadingState).lang is LangResultDomain.Ru) {
                                "Вопросы на интервью"
                            } else {
                                "Interview questions"
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

                is InterviewsViewState.ErrorState -> {
                    Log.d("interviews in state is", "Error")
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(top = 40.dp)
                    ) {
                        TopBar(
                            screenTitle = if ((interviewsState as InterviewsViewState.ErrorState).lang is LangResultDomain.Ru) {
                                "Вопросы на интервью"
                            } else {
                                "Interview questions"
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
                                text = if ((interviewsState as InterviewsViewState.ErrorState).lang is LangResultDomain.Ru) {
                                    "Что-то пошло не так..."
                                } else {
                                    "Something went wrong..."
                                }
                            )
                            YellowButton(
                                onClick = { interviewsViewModel.refresh() },
                                text = if ((interviewsState as InterviewsViewState.ErrorState).lang is LangResultDomain.Ru) {
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

                is InterviewsViewState.SuccessState -> {
                    Log.d("interviews in state is", "Success")
                    val successState = (interviewsState as InterviewsViewState.SuccessState)
                    if(successState.currentProfessionId == 0){
                        YellowButton(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Задать профессию",
                            onClick = { notFoundProfession() },
                            isEnabled = true,
                            isLoading = false
                        )
                    }
                    else{
                    MaterialsExpandableList(
                        sections = Mapper().mapToMaterialsExpandedItems(successState.materials))
                    }
                }

                is InterviewsViewState.WithoutProfessionState -> {

                }
            }
        }

        else -> {
            Log.d("interviews realy complete", "no")
            interviewsViewModel.initViewModel()
        }
    }
}