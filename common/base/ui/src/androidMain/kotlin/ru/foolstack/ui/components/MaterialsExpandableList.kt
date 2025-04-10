package ru.foolstack.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.foolstack.ui.ext.materialSection
import ru.foolstack.ui.model.Chip
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.model.MaterialSectionItem
import ru.foolstack.ui.theme.LoadingIndicatorBackground
import ru.foolstack.ui.theme.MainYellow

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MaterialsExpandableList(
    isAsModeActive: Boolean,
    sections: List<MaterialSectionItem>,
    chips: List<Chip>,
    selectedChips: List<String>,
    selectedChip: MutableState<String>,
    onclickChip: () -> Unit,
    onClickMaterial: (Int) -> Unit = {},
    onSendComment: (Int) -> Unit = {},
    onChangeProfession: () -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    isShowBanner:Boolean,
    onClickBanner: () -> Unit,
    lang: Lang) {
    var clickEnabled by remember { mutableStateOf(true) }

    val filteredMaterials = HashSet<MaterialSectionItem>()
    selectedChips.filter { it.isNotEmpty() }.forEach { chip->
        sections.forEach { materialItem->
            if(materialItem.materialTags.contains(chip)){
                filteredMaterials.add(materialItem)
            }
        }
    }

    val isExpandedMap = remember {
        List(filteredMaterials.size) { index: Int -> index to false }
            .toMutableStateMap()
    }

    val state = rememberPullToRefreshState()

    val scaleFraction = {
        if (isRefreshing) 1f
        else LinearOutSlowInEasing.transform(state.distanceFraction).coerceIn(0f, 1f)
    }

    LazyColumn(
        Modifier
            .pullToRefresh(
                state = PullToRefreshState(),
                isRefreshing = isRefreshing,
                onRefresh = {}
            )
//            .pullToRefresh(
//                state = if(isConnectionAvailable) { state } else {
//                    PullToRefreshState()
//                },
//                isRefreshing = isRefreshing,
//                onRefresh = { if(isConnectionAvailable){
//                    onRefresh()
//                }
//                }
//            )
            .padding(bottom = 20.dp),
        content = {
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
                    TopBar(screenTitle = if(lang == Lang.RU){"Вопросы на интервью"}else{"Interview questions"}, action = onChangeProfession, isBackArrow = false, isIconVisible = true)
//                    TopBar(screenTitle = if(lang == Lang.RU){"Вопросы на интервью"}else{"Interview questions"}, action = onChangeProfession, isBackArrow = false, isIconVisible = isConnectionAvailable)
                    ChipSelector(chips = chips, selectedChips = selectedChips, selectedChip = selectedChip, onclickChip = onclickChip)
                }
            }
            if(isShowBanner){
                filteredMaterials.onEachIndexed { index, sectionData ->
                    if(index<30){
                        materialSection(
                            sectionData = sectionData,
                            isExpanded = isExpandedMap[index] ?: false,
                            onHeaderClick = {
                                isExpandedMap[index] = !(isExpandedMap[index] ?: true)
                            },
                            onDetailsClick = {
                                if(clickEnabled){
                                    clickEnabled = false
                                    onClickMaterial(sectionData.materialId)
                                }
                            },
                            sendCommentClick = {
                                if(clickEnabled){
                                    clickEnabled = false
                                    onSendComment(sectionData.materialId)
                                }
                            },
                            lang = lang
                        )
                    }
                }
            }
            else{
                filteredMaterials.onEachIndexed { index, sectionData ->
                    materialSection(
                        sectionData = sectionData,
                        isExpanded = isExpandedMap[index] ?: false,
                        onHeaderClick = {
                            isExpandedMap[index] = !(isExpandedMap[index] ?: true)
                        },
                        onDetailsClick = {
                            onClickMaterial(sectionData.materialId)
                        },
                        sendCommentClick = {
                            onSendComment(sectionData.materialId)
                        },
                        lang = lang
                    )
                }
            }
            if(isShowBanner){
                if(!isAsModeActive){
                    item {
                        ProfessionsSaleBanner(
                            text = if(lang == Lang.RU){ "Для того, чтобы открыть весь список вопросов, тебе следует приобрести соответствующую профессию. Напиши нам в Telegram." } else { "In order to open the full list of questions, it should buy the profession. Write to us in Telegram." },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp, horizontal = 20.dp),
                            onClick = onClickBanner)
                    }
                }
            }
        }
    )
}