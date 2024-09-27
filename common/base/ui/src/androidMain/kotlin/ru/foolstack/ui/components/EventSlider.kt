package ru.foolstack.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.foolstack.ui.model.EventItem
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.utils.decodeBase64ToBitmap


@Composable
fun EventSlider(lang: Lang, images: List<EventItem>) {
    var currentImageIndex by remember { mutableIntStateOf(0) }
    var isAnimating by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxWidth()) {
        Title(text = if(lang==Lang.RU){ "Ближайшие мероприятия" } else{ "Events" })
        Box(modifier = Modifier
            .weight(1f)
            .height(100.dp)
            .fillMaxWidth()
            .padding(top = 10.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)) {
            // Scrollable Row of Cards
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(images) { index, event ->
                    Card(
                        modifier = Modifier
                            .width(280.dp)
                            .height(180.dp)
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
                            }
                    ) {
                        event.eventImageBase64.decodeBase64ToBitmap()?.let {
                            Image(
                                modifier = Modifier
                                    .width(280.dp)
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                bitmap = it,
                                contentDescription = event.eventName
                            )
                        }
                        Text(text = "Bla bla")
//                        NetworkImage(
//                            contentDescription = "",
//                            url = image as String,
//                            width = 300,
//                            height = 200
//                        )
                    }
                }

            }

        }
    }

    // Automatic Image Slider
    LaunchedEffect(currentImageIndex) {
        while (true) {
            delay(5000L)
            if (!isAnimating) {
                val nextIndex = (currentImageIndex + 1) % images.size
                currentImageIndex = nextIndex
            }
        }
    }
}