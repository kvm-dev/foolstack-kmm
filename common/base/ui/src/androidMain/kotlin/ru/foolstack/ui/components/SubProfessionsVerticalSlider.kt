package ru.foolstack.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.model.SubProfessionItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubProfessionsVerticalSlider(
    lang: Lang,
    subProfessions: MutableList<SubProfessionItem>,
    onBackPressed: () -> Unit,
    selectId: MutableState<Int>,
    isLastValue: MutableState<Boolean>,
    subProfessionId: MutableState<Int>,
    saveProfession: () -> Unit
) {
    // Scrollable Row of Cards
    LazyColumn(
        Modifier
            .padding(bottom = 20.dp)
    ) {
        stickyHeader {
            Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                TopBar(
                    screenTitle = if (lang == Lang.RU) {
                        "Выбери профессию"
                    } else {
                        "Choose a profession"
                    }, action = onBackPressed
                )
            }
        }
        itemsIndexed(subProfessions) { _, subProfessionItem ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .padding(horizontal = 32.dp, vertical = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                SubProfessionRadioButton(
                    isSelected = selectId.value == subProfessionItem.professionId,
                    text = subProfessionItem.professionName,
                    onClick = { selectId.value = subProfessionItem.professionId
                        isLastValue.value =
                            subProfessions.find { it.professionId == selectId.value }?.subProfessions?.isNotEmpty() != true
                              },
                )
            }
        }
        item {
            val yellowButtonText = if(lang == Lang.RU){
                "Выбрать"
            }
            else{
                "Select"
            }
            YellowButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 20.dp),
                text = yellowButtonText,
                onClick = {
                    subProfessionId.value = selectId.value
                    if(isLastValue.value){
                        saveProfession()
                        onBackPressed()
                    }
                    else{
                       val newList = subProfessions.find { it.professionId == selectId.value }?.subProfessions
                        subProfessions.clear()
                        if (newList != null) {
                            subProfessions.addAll(newList)
                            selectId.value = 0
                        }
                    }
                          },
                isEnabled = (selectId.value!=0),
                isLoading = false
            )
        }
    }
}