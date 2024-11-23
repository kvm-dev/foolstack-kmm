package ru.foolstack.news.impl.presentation.ui

import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.model.ProgressState
import ru.foolstack.news.impl.presentation.viewmodel.NewsCardViewModel
import ru.foolstack.ui.R
import ru.foolstack.ui.components.CardText
import ru.foolstack.ui.components.CardTitle
import ru.foolstack.ui.components.ServiceSubLabel
import ru.foolstack.ui.components.ShareButton
import ru.foolstack.ui.components.Title
import ru.foolstack.ui.components.TopBar
import ru.foolstack.ui.components.YellowButton
import ru.foolstack.ui.utils.decodeBase64ToBitmap
import ru.foolstack.ui.utils.timestampToDateString

@Composable
fun NewsCardScreen(newsCardViewModel: NewsCardViewModel = koinViewModel(), newsId: Int) {
    val backDispatcher =
        checkNotNull(LocalOnBackPressedDispatcherOwner.current).onBackPressedDispatcher
    val viewModelState by newsCardViewModel.progressState.collectAsState()
    when (viewModelState) {
        ProgressState.COMPLETED -> {
            Log.d("realy newsCard Complete", "yes")
            val newsState by newsCardViewModel.uiState.collectAsState()
            when (newsState) {

                is NewsCardViewState.Idle -> {
                    Log.d("newsCard in state is", "Error")
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(top = 40.dp)
                    ) {
                        TopBar(
                            screenTitle = if ((newsState as NewsCardViewState.Idle).lang is LangResultDomain.Ru) {
                                "Новость"
                            } else {
                                "News"
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
                                text = if ((newsState as NewsCardViewState.Idle).lang is LangResultDomain.Ru) {
                                    "Что-то пошло не так..."
                                } else {
                                    "Something went wrong..."
                                }
                            )
                            YellowButton(
                                onClick = { backDispatcher.onBackPressed() },
                                text = if ((newsState as NewsCardViewState.Idle).lang is LangResultDomain.Ru) {
                                    "Вернуться к новостям"
                                } else {
                                    "Return to news"
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

                is NewsCardViewState.SuccessState -> {
                    Log.d("newsCard in state is", "Success")
                    val successState = (newsState as NewsCardViewState.SuccessState)
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
                                successState.singleNewsDomain?.newsImageBase64?.decodeBase64ToBitmap()?.let {
                                    Image(
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .height(320.dp),
                                        bitmap = it,
                                        contentDescription = successState.singleNewsDomain.newsName
                                    )
                                    TopBar(
                                        screenTitle = successState.singleNewsDomain.newsName ?: "",
                                        action = { backDispatcher.onBackPressed() },
                                        isDark = false,
                                        isTitleVisible = false,
                                    )
                                    Column(modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .fillMaxWidth()) {
                                        Row(modifier = Modifier.padding(start = 6.dp, end = 6.dp)) {
                                            ServiceSubLabel(
                                                text = successState.singleNewsDomain.newsDate.timestampToDateString(),
                                                modifier = Modifier.align(Alignment.CenterVertically)
                                            )
                                            Spacer(modifier = Modifier.weight(1F))
                                            ShareButton(
                                                modifier = Modifier
                                                    .align(Alignment.CenterVertically)
                                                    .padding(start = 4.dp),
                                                onClick = { newsCardViewModel.shareLink(successState.singleNewsDomain.newsLink)}
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
                                                modifier = Modifier
                                                    .wrapContentHeight()
                                                    .padding(
                                                        top = 24.dp,
                                                        bottom = 34.dp,
                                                        start = 24.dp,
                                                        end = 24.dp
                                                    )
                                                    .align(Alignment.CenterStart),
                                                text = successState.singleNewsDomain.newsName,
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
                                CardText(
                                    text = successState.singleNewsDomain?.newsText ?: "",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        else -> {
            Log.d("newsCard realy complete", "no")
            newsCardViewModel.initViewModel(newsId = newsId)
        }
    }
}