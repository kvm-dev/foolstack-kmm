package ru.foolstack.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import ru.foolstack.ui.model.EventItem
import ru.foolstack.ui.model.Chip
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.utils.decodeBase64ToBitmap
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import ru.foolstack.ui.R
import ru.foolstack.ui.theme.LoadingIndicatorBackground
import ru.foolstack.ui.theme.MainYellow

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EventVerticalSlider(
    isAsActive: Boolean,
    lang: Lang,
    events: List<EventItem>,
    chips: List<Chip>,
    selectedChips: List<String>,
    onBackPressed: () -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    onClickEvent: () -> Unit,
    selectId: MutableState<Int>,
    selectedChip: MutableState<String>,
    onclickChip: () -> Unit,
    isConnectionAvailable: Boolean
) {
    val filteredEvents = HashSet<EventItem>()
    selectedChips.filter { it.isNotEmpty() }.forEach { chip->
        events.forEach { event->
            if(isAsActive){
                if(event.eventCost==0){
                    if(event.eventTags.contains(chip)){
                        filteredEvents.add(event)
                    }
                }
            }
            else{
                if(event.eventTags.contains(chip)){
                    filteredEvents.add(event)
                }
            }
        }
    }
    val state = rememberPullToRefreshState()

    val scaleFraction = {
        if (isRefreshing) 1f
        else LinearOutSlowInEasing.transform(state.distanceFraction).coerceIn(0f, 1f)
    }

    // Scrollable Row of Cards
                LazyColumn(
                    Modifier.pullToRefresh(
                        state = if(isConnectionAvailable) { state } else {
                            PullToRefreshState()
                        },
                        isRefreshing = isRefreshing,
                        onRefresh = { if(isConnectionAvailable){
                            onRefresh()
                        }
                        }
                    ).padding(bottom = 20.dp)
                ) {
                    stickyHeader{
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
                                PullToRefreshDefaults.Indicator(state = state, isRefreshing = isRefreshing, color = MaterialTheme.colorScheme.MainYellow, containerColor = MaterialTheme.colorScheme.LoadingIndicatorBackground)
                            }
                            TopBar(screenTitle = if(lang == Lang.RU){"События"}else{"Events"}, action = onBackPressed)
                            ChipSelector(chips = chips, selectedChips = selectedChips, selectedChip = selectedChip, onclickChip = onclickChip)
                        }
                    }
                    itemsIndexed(filteredEvents.toList()) { _, event ->
                        val cost: String
                        val symbol: String = if (lang == Lang.RU) {
                            "₽"
                        } else {
                            "$"
                        }
                        cost = if (event.eventCost > 0) {
                            "${event.eventCost} $symbol"
                        } else {
                            if (lang == Lang.RU) {
                                "бесплатно"
                            } else {
                                "free"
                            }
                        }
                        var subTags = ""
                        event.eventTags.forEach { tag ->
                            subTags += "$tag/"
                        }
                        if (subTags.isNotEmpty()) {
                            subTags = subTags.substring(0, subTags.length - 1)
                        }
                        Card(
                            modifier = Modifier
                                .clickable {
                                    selectId.value = event.eventId
                                    onClickEvent()
                                }
                                .padding(horizontal = 20.dp, vertical = 18.dp),
                                colors = CardDefaults.cardColors(
                                containerColor = Color.Transparent,
                            ),
                        ) {
                            Column {
                                if(event.eventImageBase64.isNotEmpty()){
                                    event.eventImageBase64.decodeBase64ToBitmap()?.let {
                                        Image(
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(10.dp)),
                                            bitmap = it,
                                            contentDescription = event.eventName
                                        )
                                    }
                                }
                                else{
                                        Image(
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(10.dp)),
                                            painter = painterResource(R.drawable.error_loading_image_big),
                                            contentDescription = event.eventName
                                        )
                                    }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 10.dp, end = 4.dp)
                                        .align(Alignment.End)
                                ) {
                                    Column(modifier = Modifier
                                        .weight(2F)
                                        .padding(end = 2.dp)) {
                                        ServiceTag(subTags)
                                        ServiceTitle(modifier = Modifier.padding(end = 4.dp), text = event.eventName)
                                    }
                                    Column {
                                        ServiceSubLabel(
                                            text = event.eventStartDate, modifier = Modifier
                                                .align(Alignment.End)
                                        )
                                        ServiceText(modifier = Modifier.align(Alignment.CenterHorizontally), text = cost)
                                    }
                                }

                            }
                        }
                    }
                }
            }