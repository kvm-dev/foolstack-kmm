package ru.foolstack.interview.impl.presentation.ui

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
import ru.foolstack.ui.components.CommentBottomSheet
import ru.foolstack.ui.components.NotFoundData
import ru.foolstack.ui.model.Lang

@Composable
fun InterviewsScreen(
    interviewsViewModel: InterviewsViewModel = koinViewModel(),
    navController: NavController,
    interviewDestination: String,
    selectProfession: () -> Unit) {
    val materialId  = remember { mutableIntStateOf(0) }
    val selectedFilter  = remember { mutableStateOf("") }
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
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

    val isShowBottomSheet = remember {
        mutableStateOf(false)
    }
    val commentText = remember {
        mutableStateOf("")
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
                    Log.d("state is", "Loading")
                    LoadingScreen(lang = interviewsViewModel.getCurrentLang(), selectProfession = selectProfession)
                }

                is InterviewsViewState.ErrorState -> {
                    Log.d("interviews in state is", "Error")
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(top = 40.dp)
                    ) {
                        TopBar(screenTitle = if((interviewsState as InterviewsViewState.ErrorState).lang is LangResultDomain.Ru){"Вопросы на интервью"}else{"Interview questions"}, action = selectProfession, isBackArrow = false)
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
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .fillMaxSize()
                                .padding(top = 40.dp)
                        ) {
                            TopBar(screenTitle = if((interviewsState as InterviewsViewState.SuccessState).lang is LangResultDomain.Ru){"Вопросы на интервью"}else{"Interview questions"}, action = selectProfession, isBackArrow = false, isIconVisible = interviewsViewModel.isConnectionAvailable())
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
                                    text = if ((interviewsState as InterviewsViewState.SuccessState).lang is LangResultDomain.Ru) {
                                        "Прежде чем увидеть вопросы, необходимо определиться с профессией"
                                    } else {
                                        "Before you see the questions, you need to decide on a profession"
                                    }
                                )
                                YellowButton(
                                    onClick = selectProfession,
                                    text = if ((interviewsState as InterviewsViewState.SuccessState).lang is LangResultDomain.Ru) {
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
                    MaterialsExpandableList(
                        isAsModeActive = interviewsViewModel.asMode,
                        sections = Mapper().mapToMaterialsExpandedItems(successState.materials),
                        lang = if (successState.lang is LangResultDomain.Ru) {
                            Lang.RU
                        } else {
                            Lang.ENG
                        },
                        chips = Mapper().mapToChips(
                            successState.materials
                        ),
                        selectedChips = successState.selectedFilters,
                        onChangeProfession = selectProfession,
                        onRefresh = {
                            onRefresh()
                        },
                        isRefreshing = isRefreshing,
                        onClickMaterial = {
                                interviewsViewModel.navigateToMaterial(
                                    navController = navController,
                                    materialId = it,
                                    interviewDestination = interviewDestination
                                )
                        },
                        selectedChip = selectedFilter,
                        onclickChip = {interviewsViewModel.updateFilters(selectedFilter.value)},
                        onSendComment = {
                            materialId.intValue = it
                            isShowBottomSheet.value = true },
                        isShowBanner = successState.isShowBanner,
                        onClickBanner = {interviewsViewModel.goToTelegram()})
                    }
                    CommentBottomSheet(
                        isShowBottomSheet = isShowBottomSheet,
                        text = commentText,
                        lang = if(successState.lang is LangResultDomain.Ru){ Lang.RU } else { Lang.ENG },
                        sendComment = { interviewsViewModel.sendComment(materialId = materialId.intValue, comment = commentText.value)
                                        isShowBottomSheet.value = false
                                        commentText.value = ""},
                        onValueChange = { commentText.value = it }
                    )
                }

                is InterviewsViewState.WithoutProfessionState -> {

                }

                is InterviewsViewState.EmptyState->{
                        NotFoundData(titleText = interviewsViewModel.getNotFoundDataTitle(), descriptionText = interviewsViewModel.getNotFoundDataDescription())

                }
            }
        }

        ProgressState.LOADING -> {
            LoadingScreen(lang = interviewsViewModel.getCurrentLang(), selectProfession = selectProfession)
            Log.d("interviews realy loading", "yes")
            interviewsViewModel.initViewModel()
        }

        else -> {
            Log.d("interviews realy complete", "no")
            interviewsViewModel.initViewModel()
        }
    }
}

@Composable
fun LoadingScreen(lang: LangResultDomain,  selectProfession: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
    ) {
        TopBar(screenTitle = if(lang is LangResultDomain.Ru){"Вопросы на интервью"}else{"Interview questions"}, action = selectProfession, isBackArrow = false)
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
        repeat(20) {
            Column {
                ShimmerEffect(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 12.dp)
                        .background(Color.LightGray, RoundedCornerShape(10)),
                    durationMillis = 1000
                )
            }
        }
    }
}