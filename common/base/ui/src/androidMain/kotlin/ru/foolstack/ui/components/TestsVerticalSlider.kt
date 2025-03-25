package ru.foolstack.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.datetime.Clock
import ru.foolstack.ui.R
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.model.TestItem
import ru.foolstack.ui.theme.LoadingIndicatorBackground
import ru.foolstack.ui.theme.MainYellow
import ru.foolstack.ui.utils.decodeBase64ToBitmap

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TestsVerticalSlider(
    lang: Lang,
    tests: List<TestItem>,
    onChangeProfession: () -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    onClickTest: () -> Unit,
    selectId: MutableState<Int>,
    isShowDialog: MutableState<Boolean>,
    isConnectionAvailable: Boolean
) {
    val state = rememberPullToRefreshState()
    var clickEnabled by remember { mutableStateOf(true) }

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
                TopBar(screenTitle = if(lang == Lang.RU){"Тесты"}else{"Tests"}, action = onChangeProfession, isBackArrow = false, isIconVisible = isConnectionAvailable)
            }
        }

        itemsIndexed(tests.toList()) { _, test ->
            var lastResult = "Last result: "
            var questionsCount = "Questions count: "
            var timeLimit = "Time limit: "
            var difficulty = "Difficulty: "
            var nextAttempt = "Next attempt no earlier than "
            var secunds = " sec"

          if (lang == Lang.RU) {
              lastResult = "Прошлый результат: "
              questionsCount = "Количество вопросов: "
              timeLimit = "Ограничение по времени: "
              difficulty = "Сложность: "
              nextAttempt = "Повторная попытка не ранее "
              secunds = " сек"

            }
            
            Card(
                modifier = Modifier
                    .clickable(enabled = clickEnabled) {
                        val currentTime  = Clock.System.now().toEpochMilliseconds()/1000
                        selectId.value = test.testId
                        if(test.nextTry!=null){
                         if(currentTime<test.nextTry){
                             isShowDialog.value = true
                         }
                            else{
                             clickEnabled = false
                             onClickTest()
                         }
                    }
                        else{
                            clickEnabled = false
                            onClickTest()
                        }
                    }
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                    colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = with (Modifier){
                        fillMaxSize()
                            .paint(
                                // Replace with your image id
                                painterResource(id = R.drawable.tests_background),
                                contentScale = ContentScale.FillBounds)

                    })
                {
                Column(modifier = Modifier
                    .padding(10.dp)) {
                    val shape = RoundedCornerShape(10.dp)
                    TestsTitle(text = test.testName, modifier = Modifier)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, end = 4.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        TestsText(text = lastResult, modifier = Modifier)
                        TestsText(text = "${test.lastResult} %", modifier = Modifier)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, end = 4.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        TestsText(text = questionsCount, modifier = Modifier)
                        TestsText(text = "${test.questionsSize}", modifier = Modifier)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, end = 4.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        TestsText(text = timeLimit, modifier = Modifier)
                        TestsText(text = "${test.timeLimit} $secunds", modifier = Modifier)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, end = 4.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        TestsText(text = difficulty, modifier = Modifier)
                        TestsText(text = "${test.difficulty}", modifier = Modifier)
                    }

                    if(test.nextTry!=null){
                        TestsLastAttempt(value = test.nextTry, text = nextAttempt, modifier = Modifier)
                    }
                }
                }
            }
        }
    }
}