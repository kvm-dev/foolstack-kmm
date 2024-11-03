package ru.foolstack.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.foolstack.ui.model.EventsChip

@Composable
fun ChipSelector(chips: List<EventsChip>, selectedChips: SnapshotStateList<String>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            itemsIndexed(chips) { index, chip ->
                Chip(id = chip.id, label = chip.name, isSelected = selectedChips.contains(chip.name),
                    onClick = {if(selectedChips.contains(chip.name)){selectedChips.remove(chip.name)}
                    else { selectedChips.add(chip.name)} })
            }
        }
    }
}
