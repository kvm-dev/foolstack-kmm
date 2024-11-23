package ru.foolstack.interview.impl.presentation.ui

import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.foolstack.interview.impl.presentation.viewmodel.InterviewCardViewModel
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.model.ProgressState
import ru.foolstack.ui.R
import ru.foolstack.ui.components.CardBoldItalicTitle
import ru.foolstack.ui.components.CommentBottomSheet
import ru.foolstack.ui.components.CommentButton
import ru.foolstack.ui.components.CommentButtonWithText
import ru.foolstack.ui.components.InterviewWebView
import ru.foolstack.ui.components.Title
import ru.foolstack.ui.components.TopBar
import ru.foolstack.ui.components.YellowButton
import ru.foolstack.ui.model.Lang

@Composable
fun InterviewCardScreen(interviewCardViewModel: InterviewCardViewModel = koinViewModel(), materialId: Int) {
    val backDispatcher =
        checkNotNull(LocalOnBackPressedDispatcherOwner.current).onBackPressedDispatcher
    val viewModelState by interviewCardViewModel.progressState.collectAsState()
    when (viewModelState) {
        ProgressState.COMPLETED -> {
            Log.d("realy interviewCard Complete", "yes")
            val interviewState by interviewCardViewModel.uiState.collectAsState()
            when (interviewState) {
                is InterviewCardViewState.Idle -> {
                    Log.d("interviewCard in state is", "Error")
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(top = 40.dp)
                    ) {
                        TopBar(
                            screenTitle = if ((interviewState as InterviewCardViewState.Idle).lang is LangResultDomain.Ru) {
                                "Книга"
                            } else {
                                "Book"
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
                                text = if ((interviewState as InterviewCardViewState.Idle).lang is LangResultDomain.Ru) {
                                    "Что-то пошло не так..."
                                } else {
                                    "Something went wrong..."
                                }
                            )
                            YellowButton(
                                onClick = { backDispatcher.onBackPressed() },
                                text = if ((interviewState as InterviewCardViewState.Idle).lang is LangResultDomain.Ru) {
                                    "Вернуться к списку вопросов"
                                } else {
                                    "Return to interview questions"
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

                is InterviewCardViewState.SuccessState -> {
                    val commentText = remember { mutableStateOf("") }
                    val isBottomSheetVisible = remember { mutableStateOf(false) }
                    Log.d("interviewCard in state is", "Success")
                    val successState = (interviewState as InterviewCardViewState.SuccessState)
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        Column(modifier = Modifier.align(Alignment.TopCenter)) {
                            TopBar(
                                screenTitle = "",
                                action = { backDispatcher.onBackPressed() },
                                isDark = true,
                                isTitleVisible = false,
                            )
                            Column(modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth()
                                .padding(top = 2.dp, start = 20.dp, end = 20.dp)) {
                                CardBoldItalicTitle(text = successState.material?.materialName?:"",
                                    modifier = Modifier
                                        .padding(start = 60.dp, end = 60.dp, bottom = 10.dp)
                                        .align(Alignment.CenterHorizontally))
                                Column(modifier = Modifier.
                                verticalScroll(rememberScrollState())) {
                                    successState.material?.materialText?.let { InterviewWebView(it) }
                                    CommentButtonWithText(
                                        modifier = Modifier
                                            .padding(16.dp),
                                        lang = if(successState.lang is LangResultDomain.Ru){
                                            Lang.RU} else{ Lang.ENG},
                                        onClick = {if(!isBottomSheetVisible.value){isBottomSheetVisible.value = true} })
                                }
                            }
                        }
                    }
                    CommentBottomSheet(
                        isShowBottomSheet = isBottomSheetVisible,
                        text = commentText,
                        lang = if(successState.lang is LangResultDomain.Ru){ Lang.RU } else { Lang.ENG },
                        sendComment = { successState.material?.materialId?.let { interviewCardViewModel.sendComment(materialId = it, comment = commentText.value) }
                            isBottomSheetVisible.value = false
                            commentText.value = ""},
                        onValueChange = { commentText.value = it }
                    )
                }
            }
        }

        else -> {
            Log.d("interviewCard realy complete", "no")
            interviewCardViewModel.initViewModel(materialId = materialId)
        }
    }
}