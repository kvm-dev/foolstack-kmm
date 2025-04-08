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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.foolstack.ui.R
import ru.foolstack.ui.model.Chip
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.model.StudyItem
import ru.foolstack.ui.theme.LoadingIndicatorBackground
import ru.foolstack.ui.theme.MainYellow
import ru.foolstack.ui.utils.decodeBase64ToBitmap

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StudiesVerticalSlider(
    isAsModeActive: Boolean,
    lang: Lang,
    studies: List<StudyItem>,
    prText: String,
    chips: List<Chip>,
    selectedChips: List<String>,
    onBackPressed: () -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    onClickStudy: () -> Unit,
    selectId: MutableState<Int>,
    selectedChip: MutableState<String>,
    onclickChip: () -> Unit,
    isConnectionAvailable: Boolean
) {
    val filteredStudies = HashSet<StudyItem>()
    selectedChips.filter { it.isNotEmpty() }.forEach { chip->
        studies.forEach { study->
            if(study.studyTags.contains(chip)){
                filteredStudies.add(study)
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
                TopBar(screenTitle = if(lang == Lang.RU){"Обучение"}else{"Education"}, action = onBackPressed)
                ChipSelector(chips = chips, selectedChips = selectedChips, selectedChip = selectedChip, onclickChip = onclickChip)
            }
        }
        if(!isAsModeActive){
            item{
                ServiceDescription(
                    modifier = Modifier
                        .padding(horizontal = 32.dp),
                    text = prText)
            }
        }
        itemsIndexed(filteredStudies.toList()) { _, study ->
            val symbol: String = if (lang == Lang.RU) {
                "₽"
            } else {
                "$"
            }
            var subTags = ""
            study.studyTags.forEach { tag ->
                subTags += "$tag/"
            }
            if (subTags.isNotEmpty()) {
                subTags = subTags.substring(0, subTags.length - 1)
            }
            Card(
                modifier = Modifier
                    .clickable {
                        selectId.value = study.studyId
                        onClickStudy()
                    }
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                ),
            ) {
                Column {
                    val shape = RoundedCornerShape(10.dp)
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape)
                    ){
                        if(study.studyImageBase64.isNotEmpty()){
                            study.studyImageBase64.decodeBase64ToBitmap()?.let {
                                Image(
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(shape),
                                    bitmap = it,
                                    contentDescription = study.studyName
                                )
                            }
                        }
                        else{
                            Image(
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape),
                                painter = painterResource(R.drawable.error_loading_image_big),
                                contentDescription = study.studyName
                            )
                        }
                        if(study.studySalePercent>0){
                            if(!isAsModeActive){
                                StudySale(modifier = Modifier
                                    .align(Alignment.TopStart), text = if(lang == Lang.RU){"Скидка ${study.studySalePercent}%"} else {"Discount ${study.studySalePercent}%"})
                            }
                        }
                        if(study.studyAdditionalText.isNotEmpty()){
                            if(!isAsModeActive){
                                StudyAdditional(modifier = Modifier.align(Alignment.BottomStart), text = study.studyAdditionalText)
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, end = 4.dp)
                            .align(Alignment.End)
                    ) {
                        val payPerPeriod = when(study.studyLengthType){
                            0-> if(lang == Lang.RU){"мес"} else {"month"}
                            1-> if(lang == Lang.RU){"нед"} else {"week"}
                            else-> if(lang == Lang.RU){"день"} else {"day"}
                        }
                        Column(modifier = Modifier
                            .weight(2F)
                            .padding(end = 2.dp)) {
                            val lengthText = if(lang == Lang.RU){"Длительность"} else {"Duration"}
                            val lengthType = when(study.studyLengthType){
                                0-> if(lang == Lang.RU){"мес"} else {"months"}
                                1-> if(lang == Lang.RU){"нед"} else {"weeks"}
                                else-> if(lang == Lang.RU){"дней"} else {"days"}
                            }
                            if(!isAsModeActive){
                                ServiceTag("$lengthText $lengthType")
                            }
                            ServiceTitle(modifier = Modifier.padding(top = 8.dp), text = study.studyName)
                        }
                        Column(modifier = Modifier) {
                            if(!isAsModeActive){
                                ServiceSubLabel(
                                    text = if(lang == Lang.RU){"от ${study.studyCost} $symbol/$payPerPeriod"} else {"from ${study.studyCost} $symbol/$payPerPeriod"}, modifier = Modifier
                                        .align(Alignment.End)
                                )
                            }
                            ServiceText(modifier = Modifier.align(Alignment.End).padding(end = 6.dp, top = 8.dp), text = study.studyOwner)
                        }
                    }

                }
            }
        }
    }
}