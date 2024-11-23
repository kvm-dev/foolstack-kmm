package ru.foolstack.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.foolstack.ui.R
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.model.ProfessionItem
import ru.foolstack.ui.theme.LoadingIndicatorBackground
import ru.foolstack.ui.theme.MainYellow
import ru.foolstack.ui.utils.decodeBase64ToBitmap

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfessionsVerticalSlider(
    lang: Lang,
    professions: List<ProfessionItem>,
    onBackPressed: () -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    onClickProfession: () -> Unit,
    selectProfessionId: MutableState<Int>
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
                state = state,
                isRefreshing = isRefreshing,
                onRefresh = onRefresh
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
                    screenTitle = if (lang == Lang.RU) {
                        "Выбери направление"
                    } else {
                        "Career direction"
                    }, action = onBackPressed
                )
            }
        }
        itemsIndexed(professions) { _, professionItem ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        selectProfessionId.value = professionItem.professionId
                        onClickProfession()
                    }
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column{
                    if (professionItem.professionImageBase64.isNotEmpty()) {
                        professionItem.professionImageBase64.decodeBase64ToBitmap()?.let {
                            Image(
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp)),
                                bitmap = it,
                                contentDescription = professionItem.professionName
                            )
                        }
                    } else {
                        Image(
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp)),
                            painter = painterResource(R.drawable.bug_icon),
                            contentDescription = professionItem.professionName
                        )
                    }

                    ExtraBoldTitle(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                        text = professionItem.professionName
                    )
                }
            }
        }
    }
}