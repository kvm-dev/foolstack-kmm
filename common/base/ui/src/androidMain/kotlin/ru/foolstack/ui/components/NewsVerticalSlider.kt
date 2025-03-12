package ru.foolstack.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.foolstack.ui.R
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.model.NewsItem
import ru.foolstack.ui.theme.LoadingIndicatorBackground
import ru.foolstack.ui.theme.MainYellow
import ru.foolstack.ui.utils.decodeBase64ToBitmap
import ru.foolstack.ui.utils.timestampToDateString

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewsVerticalSlider(
    lang: Lang,
    news: List<NewsItem>,
    onBackPressed: () -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    onClickNews: () -> Unit,
    selectId: MutableState<Int>,
    isConnectionAvailable: Boolean
) {

    val state = rememberPullToRefreshState()

    val scaleFraction = {
        if (isRefreshing) 1f
        else LinearOutSlowInEasing.transform(state.distanceFraction).coerceIn(0f, 1f)
    }

    // Scrollable Row of Cards
    LazyColumn(
        Modifier
            .pullToRefresh(
                state = if(isConnectionAvailable) { state } else {
                    PullToRefreshState()
                },
                isRefreshing = isRefreshing,
                onRefresh = { if(isConnectionAvailable){
                    onRefresh()
                }
                }
            )
            .padding(bottom = 20.dp)
    ) {
        stickyHeader {
            Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                Box(
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .graphicsLayer {
                            scaleX = scaleFraction()
                            scaleY = scaleFraction()
                        }
                        .zIndex(1F)
                ) {
                    PullToRefreshDefaults.Indicator(
                        state = state,
                        isRefreshing = isRefreshing,
                        color = MaterialTheme.colorScheme.MainYellow,
                        containerColor = MaterialTheme.colorScheme.LoadingIndicatorBackground
                    )
                }
                TopBar(
                    isIconVisible = false,
                    screenTitle = if (lang == Lang.RU) {
                        "Новости"
                    } else {
                        "News"
                    }, action = onBackPressed
                )
            }
        }
        itemsIndexed(news) { _, newsItem ->

            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 18.dp)
            ) {
                if (newsItem.newsImageBase64.isNotEmpty()) {
                    newsItem.newsImageBase64.decodeBase64ToBitmap()?.let {
                        Image(
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp)),
                            bitmap = it,
                            contentDescription = newsItem.newsName
                        )
                    }
                } else {
                    Image(
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp)),
                        painter = painterResource(R.drawable.error_loading_image_big),
                        contentDescription = newsItem.newsName
                    )
                }

                        NewsTitle(modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp),
                            text = newsItem.newsName)
                        NewsDate(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            text = newsItem.newsDate.timestampToDateString()
                        )
                    YellowButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        text = if(lang == Lang.RU){" Подробнее "} else{ "Read more"},
                        onClick = {
                            selectId.value = newsItem.newsId
                            onClickNews() },
                        isEnabled = true,
                        isLoading = false
                    )
            }
        }
    }
}