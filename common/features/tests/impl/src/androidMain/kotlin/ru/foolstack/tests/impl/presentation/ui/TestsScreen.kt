package ru.foolstack.tests.impl.presentation.ui

import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import kotlinx.datetime.Clock
import org.koin.androidx.compose.koinViewModel
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.model.ProgressState
import ru.foolstack.tests.impl.mapper.Mapper
import ru.foolstack.tests.impl.presentation.viewmodel.TestsViewModel
import ru.foolstack.ui.R
import ru.foolstack.ui.components.GreenDialog
import ru.foolstack.ui.components.TestsVerticalSlider
import ru.foolstack.ui.components.ShimmerEffect
import ru.foolstack.ui.components.Title
import ru.foolstack.ui.components.TopBar
import ru.foolstack.ui.components.YellowButton
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.utils.timestampToDateString

@Composable
fun TestsScreen(
    testsViewModel: TestsViewModel = koinViewModel(),
    navController: NavController,
    testDestination: String,
    selectProfession: () -> Unit) {
    val testId  = remember { mutableIntStateOf(0) }
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            // fetch something
            delay(2000)
            testsViewModel.refresh()
            isRefreshing = false
        }
    }

    LifecycleResumeEffect(Unit) {
        testsViewModel.initViewModel()

        onPauseOrDispose {
        }
    }

    val viewModelState by testsViewModel.progressState.collectAsState()
    when (viewModelState) {
        ProgressState.COMPLETED -> {
            Log.d("realy tests Complete", "yes")
            val testsState by testsViewModel.uiState.collectAsState()
            when (testsState) {
                is TestsViewState.LoadingState -> {
                    Log.d("state is", "Loading")
                    LoadingScreen(lang = testsViewModel.getCurrentLang(), selectProfession = selectProfession)
                }

                is TestsViewState.ErrorState -> {
                    Log.d("tests in state is", "Error")
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(top = 40.dp)
                    ) {
                        TopBar(screenTitle = if((testsState as TestsViewState.ErrorState).lang is LangResultDomain.Ru){"Тесты"}else{"Tests"}, action = selectProfession, isBackArrow = false)
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
                                text = if ((testsState as TestsViewState.ErrorState).lang is LangResultDomain.Ru) {
                                    "Что-то пошло не так..."
                                } else {
                                    "Something went wrong..."
                                }
                            )
                            YellowButton(
                                onClick = { testsViewModel.refresh() },
                                text = if ((testsState as TestsViewState.ErrorState).lang is LangResultDomain.Ru) {
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

                is TestsViewState.SuccessState -> {
                    Log.d("tests in state is", "Success")
                    val successState = (testsState as TestsViewState.SuccessState)
                    if(successState.currentProfessionId == 0){
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .fillMaxSize()
                                .padding(top = 40.dp)
                        ) {
                            TopBar(screenTitle = if((testsState as TestsViewState.SuccessState).lang is LangResultDomain.Ru){"Тесты"}else{"Tests"}, action = selectProfession, isBackArrow = false)
                            Column(modifier = Modifier.align(Alignment.Center)) {
                                val bugBitmap = ImageBitmap.imageResource(id = R.drawable.fs_logo)
                                Image(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(100.dp, 10.dp),
                                    bitmap = bugBitmap,
                                    contentDescription = "FoolStack"
                                )
                                Title(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    text = if ((testsState as TestsViewState.SuccessState).lang is LangResultDomain.Ru) {
                                        "Прежде чем увидеть список тестов, необходимо определиться с профессией"
                                    } else {
                                        "Before you see the tests, you need to decide on a profession"
                                    }
                                )
                                YellowButton(
                                    onClick = selectProfession,
                                    text = if ((testsState as TestsViewState.SuccessState).lang is LangResultDomain.Ru) {
                                        "Выбрать профессию"
                                    } else {
                                        "Select profession"
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
                    else{
                        val isVisibleDialog = remember { mutableStateOf(false)  }
                        TestsVerticalSlider(
                            tests = Mapper().mapTestsDomainToTestsItems(tests = successState.tests, passedTests = successState.passedTests),
                            lang = if (successState.lang is LangResultDomain.Ru) {
                                Lang.RU
                            } else {
                                Lang.ENG
                            },
                            onChangeProfession = selectProfession,
                            onRefresh = {
                                onRefresh()
                            },
                            isRefreshing = isRefreshing,
                            selectId = testId,
                            onClickTest = {
                                testsViewModel.navigateToTest(
                                    navController = navController,
                                    testId = testId.intValue,
                                    testDestination = testDestination
                                )
                            },
                            isShowDialog = isVisibleDialog,
                            isConnectionAvailable = testsViewModel.isConnectionAvailable()
                        )
                        GreenDialog(
                            title = if(successState.lang is LangResultDomain.Ru){"Обрати внимание"} else {"Attention"},
                            text = if(successState.lang is LangResultDomain.Ru){"Пройти тест заново можно будет только после ${successState.passedTests.find { it.testId == testId.intValue }?.finishTestTime?.timestampToDateString()}"} else {"You will be able to take the test again no early ${successState.passedTests.find { it.testId == testId.intValue }?.finishTestTime?.timestampToDateString()}"},
                            generalActionText = if(successState.lang is LangResultDomain.Ru){"Ок, понятно"} else {"Ok, understood"},
                            hideSecondaryButton = true,
                            onGeneralActionClick = { isVisibleDialog.value = false },
                            onSecondaryActionClick = {},
                            isVisible = isVisibleDialog
                            )
                    }
                }
            }
        }

        ProgressState.LOADING -> {
            LoadingScreen(lang = testsViewModel.getCurrentLang(), selectProfession = selectProfession)
            Log.d("tests realy loading", "yes")
            testsViewModel.initViewModel()
        }

        else -> {
            Log.d("tests realy complete", "no")
            testsViewModel.initViewModel()
        }
    }
}

@Composable
fun LoadingScreen(lang: LangResultDomain, selectProfession: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
    ) {
        TopBar(screenTitle = if(lang is LangResultDomain.Ru){"Тесты"}else{"Tests"}, action = selectProfession, isBackArrow = false)
        repeat(20) {
            Column {
                ShimmerEffect(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 16.dp)
                        .background(Color.LightGray, RoundedCornerShape(10)),
                    durationMillis = 1000
                )
            }
        }
    }
}