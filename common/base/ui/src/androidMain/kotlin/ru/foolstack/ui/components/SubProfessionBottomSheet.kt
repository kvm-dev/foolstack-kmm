package ru.foolstack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.model.SubProfessionItem
import ru.foolstack.ui.theme.DisabledColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubProfessionBottomSheet(
    isShowBottomSheet: MutableState<Boolean>,
    subProfessionsList: List<SubProfessionItem>,
    selectedSubProfession: MutableState<Int>,
    subProfessionId: MutableState<Int>,
    saveProfession: () -> Unit,
    lang: Lang
) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isShowBottomSheet.value) {
                ModalBottomSheet(
                    onDismissRequest = {
                        isShowBottomSheet.value = false
                        selectedSubProfession.value = 0
                        subProfessionId.value = 0
                    },
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    containerColor = MaterialTheme.colorScheme.background,
                    tonalElevation = 16.dp,
                    dragHandle = {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .width(50.dp)
                                .height(6.dp)
                                .clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.DisabledColor)
                                .align(Alignment.Center)
                        )
                    }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val subsList = remember {
                            mutableStateListOf(SubProfessionItem(
                                professionId = 0,
                                professionName = "",
                                subProfessions = listOf()
                            ))
                        }
                        //init
                        if(subsList.size==1 && subProfessionId.value==0){
                            subsList.clear()
                            subsList.addAll(subProfessionsList)
                        }
                        val isLastList = remember {
                            mutableStateOf(true)
                        }
                        SubProfessionsVerticalSlider(
                            lang = lang,
                            subProfessions = subsList,
                            onBackPressed = { if(isShowBottomSheet.value){
                                isShowBottomSheet.value = false
                                subProfessionId.value = 0
                                selectedSubProfession.value = 0} },
                            selectId = selectedSubProfession,
                            subProfessionId = subProfessionId,
                            isLastValue = isLastList,
                            saveProfession = saveProfession)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
}