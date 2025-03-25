package ru.foolstack.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.foolstack.ui.R
import ru.foolstack.ui.model.AchievementItem
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.theme.Divider

@Composable
fun AchievementsSlider(lang: Lang, achievements: List<AchievementItem>, isLoading: Boolean = false, selectId: MutableState<Int>, isShowDialog: MutableState<Boolean>) {
    val emptyText = if(lang== Lang.RU){ "Только зарегистрированные\nпользователи могут\nполучать достижения" } else{ "Only registered users\ncan receive\nachievements" }
    val currentImageIndex by remember { mutableIntStateOf(0) }
    var isAnimating by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxWidth().height(226.dp).padding(top = 14.dp)) {
        Title(modifier = Modifier, text = if(lang== Lang.RU){ "Твои достижения" } else{ "Your achievements" })
        Box(modifier = Modifier
            .weight(1f)
            .height(140.dp)
            .fillMaxWidth()
            .padding(top = 10.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)) {
            // Scrollable Row of Cards
            if(isLoading){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    repeat(3) {
                        ShimmerEffect(
                            modifier = Modifier
                                .size(width = 180.dp, height = 141.dp)
                                .padding(horizontal = 8.dp)
                                .background(Color.LightGray, RoundedCornerShape(16)),
                            durationMillis = 1000
                        )
                    }
                }
            }
            else if(achievements.isNotEmpty()){
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(achievements) { index, achievement ->

                    Card(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .width(160.dp)
                            .height(140.dp)
                            .clickable {
                                if (index != currentImageIndex && !isAnimating) {
                                    isAnimating = true
                                }
                                selectId.value = achievement.achievementId
                                isShowDialog.value = true
                                       },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent,
                        ),
                    ) {
                        Box {
                            val icon = when (achievement.achievementId) {
                                1 -> R.drawable.foolstaker
                                2 -> R.drawable.collocutor
                                3 -> R.drawable.commentator
                                4 -> R.drawable.mentor
                                5 -> R.drawable.reader
                                6 -> R.drawable.overachiever
                                7 -> R.drawable.idea_generator
                                8 -> R.drawable.eternal_student
                                else -> null
                            }

                            icon?.let { painterResource(it) }
                                ?.let {
                                    Image(
                                        painter = it,
                                        contentDescription = achievement.achievementDescription,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }


                            Row(
                                modifier = Modifier
                                    .padding(top = 64.dp, bottom = 68.dp)
                                    .align(Alignment.Center)
                            ) {
                                when (achievement.achievementLevel) {
                                    1 -> {
                                        Image(
                                            painter = painterResource(R.drawable.star),
                                            contentDescription = "level",
                                            modifier = Modifier
                                                .width(10.dp)
                                                .padding(horizontal = 2.dp),
                                            contentScale = ContentScale.Inside
                                        )
                                    }

                                    2 -> {
                                        Image(
                                            painter = painterResource(R.drawable.star),
                                            contentDescription = "level",
                                            modifier = Modifier
                                                .width(20.dp)
                                                .padding(end = 5.dp),
                                            contentScale = ContentScale.Inside
                                        )
                                        Image(
                                            painter = painterResource(R.drawable.star),
                                            contentDescription = "level",
                                            modifier = Modifier
                                                .width(20.dp)
                                                .padding(start = 5.dp),
                                            contentScale = ContentScale.Inside
                                        )
                                    }

                                    3 -> {
                                        Image(
                                            painter = painterResource(R.drawable.star),
                                            contentDescription = "level",
                                            modifier = Modifier
                                                .width(20.dp)
                                                .padding(horizontal = 2.dp),
                                            contentScale = ContentScale.Inside
                                        )
                                        Image(
                                            painter = painterResource(R.drawable.star),
                                            contentDescription = "level",
                                            modifier = Modifier
                                                .width(20.dp)
                                                .padding(horizontal = 2.dp),
                                            contentScale = ContentScale.Inside
                                        )
                                        Image(
                                            painter = painterResource(R.drawable.star),
                                            contentDescription = "level",
                                            modifier = Modifier
                                                .width(20.dp)
                                                .padding(horizontal = 2.dp),
                                            contentScale = ContentScale.Inside
                                        )
                                    }

                                    else -> {
                                        //nothing
                                    }
                                }
                            }
                            NotificationTitle(
                                modifier = Modifier
                                    .padding(bottom = 20.dp)
                                    .align(Alignment.BottomCenter),
                                text = achievement.achievementName
                            )
                        }
                    }
                }

            }
        }
            else{
                    Image(
                        painter = painterResource(R.drawable.empty_achievements_background),
                        contentDescription = "Not found achievements",
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Inside
                    )
                    NotificationTitle(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 46.dp),
                        text = emptyText
                    )
            }
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, top = 10.dp), color = MaterialTheme.colorScheme.Divider, thickness = 1.dp)
    }
}