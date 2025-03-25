package ru.foolstack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.foolstack.ui.model.Chip

@Composable
fun RadioChips(chips: List<Chip>,
                 selectedChip: MutableState<String>,
                 onclickChip: (String) -> Unit = {},) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.Center
        ) {
            itemsIndexed(chips) { index, chip ->
                Chip(modifier = Modifier.padding(horizontal = 6.dp), id = chip.id, label = chip.name, isSelected = selectedChip.value == chip.name,
                    onClick = {
                        selectedChip.value = chip.name
                        onclickChip(chip.name)})
            }
        }
    }
}