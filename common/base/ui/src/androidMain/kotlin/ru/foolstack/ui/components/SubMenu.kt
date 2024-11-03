package ru.foolstack.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.foolstack.ui.R
import ru.foolstack.ui.model.Lang

@Composable
fun SubMenu(lang: Lang,  onClickEvents: () -> Unit = {}) {
    var isAnimating by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val events =  if(lang== Lang.RU){ "События" } else{ "Events" }
    val books =  if(lang== Lang.RU){ "Литература" } else{ "Books" }
    val studies =  if(lang== Lang.RU){ "Обучение" } else{ "Study" }
    Column(modifier = Modifier
        .wrapContentWidth()
        .height(160.dp)) {
        Box(modifier = Modifier
            .height(160.dp)
            .wrapContentWidth()
            .align(Alignment.CenterHorizontally)
            .padding(top = 10.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)) {
            // Scrollable Row of Cards
                Row(
                    modifier = Modifier.align(Alignment.Center).fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        modifier = Modifier
                           .wrapContentWidth()
                            .clickable {
                                if (!isAnimating) {
                                    isAnimating = true
                                    coroutineScope.launch {
                                        val delayMillis = 500L
                                        // Wait for the card to change color before animating
                                        delay(delayMillis / 2)
                                        delay(delayMillis)
                                        isAnimating = false
                                        onClickEvents()
                                    }
                                }
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent,
                        ),
                    ) {
                        Column {
                                Image(
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .width(68.dp)
                                        .height(68.dp)
                                        .align(Alignment.CenterHorizontally),
                                    painter = painterResource(R.drawable.events_icon),
                                    contentDescription = events
                                )
                            NotificationTitle(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp), text = events)
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Card(
                        modifier = Modifier
                            .wrapContentWidth()
                            .clickable {
                                if (!isAnimating) {
                                    isAnimating = true
                                    coroutineScope.launch {
                                        val delayMillis = 500L
                                        // Wait for the card to change color before animating
                                        delay(delayMillis / 2)
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
                            Image(
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(68.dp)
                                    .height(68.dp)
                                    .align(Alignment.CenterHorizontally),
                                painter = painterResource(R.drawable.books_icon),
                                contentDescription = books
                            )
                            NotificationTitle(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp), text = books)
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Card(
                        modifier = Modifier
                            .wrapContentWidth()
                            .clickable {
                                if (!isAnimating) {
                                    isAnimating = true
                                    coroutineScope.launch {
                                        val delayMillis = 500L
                                        // Wait for the card to change color before animating
                                        delay(delayMillis / 2)
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
                            Image(
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(68.dp)
                                    .height(68.dp)
                                    .align(Alignment.CenterHorizontally),
                                painter = painterResource(R.drawable.study_icon),
                                contentDescription = studies
                            )
                            NotificationTitle(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp), text = studies)
                        }
                    }
                }


        }
    }

}