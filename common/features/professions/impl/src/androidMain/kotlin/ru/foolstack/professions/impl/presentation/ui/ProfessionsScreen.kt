package ru.foolstack.professions.impl.presentation.ui

import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.model.ProgressState
import ru.foolstack.professions.impl.mapper.Mapper
import ru.foolstack.professions.impl.presentation.viewmodel.ProfessionsViewModel
import ru.foolstack.ui.R
import ru.foolstack.ui.components.ProfessionsVerticalSlider
import ru.foolstack.ui.components.ShimmerEffect
import ru.foolstack.ui.components.SubProfessionBottomSheet
import ru.foolstack.ui.components.Title
import ru.foolstack.ui.components.TopBar
import ru.foolstack.ui.components.YellowButton
import ru.foolstack.ui.model.Lang

@Composable
fun ProfessionsScreen(professionsViewModel: ProfessionsViewModel = koinViewModel(), navController: NavController, navigateToMain: ()->Unit) {
    val professionId  = remember { mutableIntStateOf(0) }
    val selectedSubProfession  = remember { mutableIntStateOf(0) }
    val subProfessionId  = remember { mutableIntStateOf(0) }
    var isRefreshing by remember { mutableStateOf(false) }
    val isShowSubProfessionBottomSheet = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val backDispatcher =
        checkNotNull(LocalOnBackPressedDispatcherOwner.current).onBackPressedDispatcher
    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            // fetch something
            delay(2000)
            professionsViewModel.refresh()
            isRefreshing = false
        }
    }

    val viewModelState by professionsViewModel.progressState.collectAsState()
    when (viewModelState) {
        ProgressState.COMPLETED -> {
            Log.d("realy professions Complete", "yes")
            val professionsState by professionsViewModel.uiState.collectAsState()
            when (professionsState) {
                is ProfessionsViewState.LoadingState -> {
                    Log.d("professions in state is", "Loading")
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 40.dp)
                    ) {
                        TopBar(
                            screenTitle = if ((professionsState as ProfessionsViewState.LoadingState).lang is LangResultDomain.Ru) {
                                "Выбери направление"
                            } else {
                                "Career direction"
                            }, onBackPressed = { backDispatcher.onBackPressed() })

                        repeat(10) {
                            Column {
                                ShimmerEffect(
                                    modifier = Modifier
                                        .height(280.dp)
                                        .fillMaxWidth()
                                        .padding(start = 32.dp, end = 32.dp, top = 12.dp)
                                        .background(Color.LightGray, RoundedCornerShape(10)),
                                    durationMillis = 1000
                                )

                                ShimmerEffect(
                                    modifier = Modifier
                                        .height(60.dp)
                                        .fillMaxWidth()
                                        .padding(start = 48.dp, end = 48.dp, top = 12.dp, bottom = 16.dp)
                                        .background(Color.LightGray, RoundedCornerShape(10)),
                                    durationMillis = 1000
                                )
                            }
                        }
                    }
                }

                is ProfessionsViewState.ErrorState -> {
                    Log.d("professions in state is", "Error")
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(top = 40.dp)
                    ) {
                        TopBar(
                            screenTitle = if ((professionsState as ProfessionsViewState.ErrorState).lang is LangResultDomain.Ru) {
                                "Выбери направление"
                            } else {
                                "Career direction"
                            }, onBackPressed = { backDispatcher.onBackPressed() })
                        Column(modifier = Modifier.align(Alignment.Center)) {
                            val bugBitmap = ImageBitmap.imageResource(id = R.drawable. bug_icon)
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(100.dp, 10.dp),
                                bitmap = bugBitmap,
                                contentDescription = "FoolStack"
                            )
                            Title(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = if ((professionsState as ProfessionsViewState.ErrorState).lang is LangResultDomain.Ru) {
                                    "Что-то пошло не так..."
                                } else {
                                    "Something went wrong..."
                                }
                            )
                            YellowButton(
                                onClick = { professionsViewModel.refresh() },
                                text = if ((professionsState as ProfessionsViewState.ErrorState).lang is LangResultDomain.Ru) {
                                    "Обновить"
                                } else {
                                    "Refresh"
                                },
                                isEnabled = true,
                                isLoading = false,
                                modifier = Modifier
                                    .padding(top = 30.dp, start = 16.dp, end = 16.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }

                is ProfessionsViewState.SuccessState -> {
                    val successState = professionsState as ProfessionsViewState.SuccessState

                    ProfessionsVerticalSlider(
                        lang = if( successState.lang is LangResultDomain.Ru ){ Lang.RU} else{ Lang.ENG},
                        professions = Mapper().mapToMainProfessionsItems(successState.professions),
                        onRefresh = { onRefresh()},
                        onBackPressed = { backDispatcher.onBackPressed()},
                        isRefreshing = false,
                        onClickProfession = { isShowSubProfessionBottomSheet.value = true},
                        selectProfessionId = professionId
                        )

                        SubProfessionBottomSheet(
                            isShowBottomSheet = isShowSubProfessionBottomSheet,
                            selectedSubProfession = selectedSubProfession,
                            lang = if( successState.lang is LangResultDomain.Ru ){ Lang.RU} else{ Lang.ENG},
                            subProfessionsList = Mapper().mapToSubProfessionsItems(successState.professions?.professions?.find { it.professionId == professionId.intValue }),
                            subProfessionId = subProfessionId,
                            saveProfession = {
                                professionsViewModel.saveProfession(selectedSubProfession.intValue)
                                navigateToMain()
                            }
                        )


                }

            }
        }

        else -> {
            Log.d("professions realy complete", "no")
            professionsViewModel.initViewModel()
        }
    }
}