package ru.foolstack.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.foolstack.ui.R
import ru.foolstack.ui.model.EventItem
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.theme.Divider
import ru.foolstack.ui.utils.decodeBase64ToBitmap


@Composable
fun EventHorizontalSlider(lang: Lang, events: List<EventItem>, isLoading: Boolean = false) {
    val emptyText = if(lang== Lang.RU){ "В ближайшее время мероприятий\nне планируется" } else{ "There are not events planned\nin the near future" }
    var currentImageIndex by remember { mutableIntStateOf(0) }
    var isAnimating by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val height = if(isLoading){ 250 } else{ if(events.isNotEmpty()){ 310 } else{ 260 } }
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(height.dp)) {
        Title(modifier = Modifier, text = if(lang==Lang.RU){ "Ближайшие мероприятия" } else{ "Events" })
        Box(modifier = Modifier
            .weight(1f)
            .height(160.dp)
            .fillMaxWidth()
            .padding(top = 10.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)) {
            // Scrollable Row of Cards
            if(isLoading){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    repeat(10) {
                        ShimmerEffect(
                            modifier = Modifier
                                .size(width = 280.dp, height = 180.dp)
                                .padding(horizontal = 20.dp)
                                .background(Color.LightGray, RoundedCornerShape(10)),
                            durationMillis = 1000
                        )
                    }
                }
            }
            else if(events.isNotEmpty()){
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(events) { index, event ->
                    var cost = ""
                    var symbol = ""
                    symbol = if (lang == Lang.RU) {
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
                            .width(280.dp)
                            .clickable {
                                if (index != currentImageIndex && !isAnimating) {
                                    isAnimating = true
                                    coroutineScope.launch {
                                        val delayMillis = 500L
                                        // Wait for the card to change color before animating
                                        delay(delayMillis / 2)
                                        currentImageIndex = index
                                        delay(delayMillis)
                                        isAnimating = false
                                    }
                                }
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent,
                        ),
                    ) {
                        Column {
                            event.eventImageBase64.decodeBase64ToBitmap()?.let {
                                Image(
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .width(280.dp)
                                        .height(180.dp)
                                        .clip(RoundedCornerShape(10.dp)),
                                    bitmap = it,
                                    contentDescription = event.eventName
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                                    .align(Alignment.End)
                            ) {
                                Column(modifier = Modifier.weight(2F).padding(end = 2.dp)) {
                                    ServiceTag(subTags)
                                    ServiceTitle(event.eventName)
                                }
                                Column {
                                    ServiceDate(
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
            else{
                Image(
                    painter = painterResource(R.drawable.empty_events_background),
                    contentDescription = "Not found achievements",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Inside
                )
                NotificationTitle(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 80.dp),
                    text = emptyText
                )
            }

        }
        HorizontalDivider(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp), color = MaterialTheme.colorScheme.Divider, thickness = 1.dp)
    }

    // Automatic Image Slider
    if(events.isNotEmpty()){
    LaunchedEffect(currentImageIndex) {
        while (true) {
            delay(5000L)
            if (!isAnimating) {
                val nextIndex = (currentImageIndex + 1) % events.size
                currentImageIndex = nextIndex
            }
        }
    }
    }
}