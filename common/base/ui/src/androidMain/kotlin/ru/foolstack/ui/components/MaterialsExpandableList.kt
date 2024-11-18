package ru.foolstack.ui.components

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateMap
import ru.foolstack.ui.ext.MaterialSection
import ru.foolstack.ui.model.MaterialSectionItem

@Composable
fun MaterialsExpandableList(sections: List<MaterialSectionItem>) {
    val isExpandedMap = remember {
        List(sections.size) { index: Int -> index to false }
            .toMutableStateMap()
    }
    sections.forEach {
        Log.d("инфа1", "${it.headerText}")
    }

    LazyColumn(
        content = {
            sections.onEachIndexed { index, sectionData ->

                Log.d("инфа2", "${sectionData.headerText}")
                MaterialSection(
                    sectionData = sectionData,
                    isExpanded = isExpandedMap[index] ?: false,
                    onHeaderClick = {
                        isExpandedMap[index] = !(isExpandedMap[index] ?: true)
                    }
                )
            }
        }
    )
}