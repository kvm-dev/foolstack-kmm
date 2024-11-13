package ru.foolstack.books.impl.presentation.ui

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import ru.foolstack.books.impl.mapper.Mapper
import ru.foolstack.books.impl.presentation.viewmodel.BooksViewModel
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.model.ProgressState
import ru.foolstack.ui.R
import ru.foolstack.ui.components.BooksVerticalSlider
import ru.foolstack.ui.components.NotificationTitle
import ru.foolstack.ui.components.ShimmerEffect
import ru.foolstack.ui.components.Title
import ru.foolstack.ui.components.TopBar
import ru.foolstack.ui.components.YellowButton
import ru.foolstack.ui.ext.scrollEnabled
import ru.foolstack.ui.model.Lang

@Composable
fun BooksScreen(booksViewModel: BooksViewModel = koinViewModel(), navController: NavController, bookDestination: String) {
    val bookId  = remember { mutableStateOf(0) }
    val searchKeyWord  = remember { mutableStateOf("") }
    val selectedFilter  = remember { mutableStateOf("") }
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val backDispatcher =
        checkNotNull(LocalOnBackPressedDispatcherOwner.current).onBackPressedDispatcher
    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            // fetch something
            delay(2000)
            booksViewModel.refresh()
            isRefreshing = false
        }
    }

    val viewModelState by booksViewModel.progressState.collectAsState()
    when (viewModelState) {
        ProgressState.COMPLETED -> {
            Log.d("realy books Complete", "yes")
            val booksState by booksViewModel.uiState.collectAsState()
            when (booksState) {
                is BooksViewState.LoadingState -> {
                    Log.d("books in state is", "Loading")
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 20.dp)){
                        // Scrollable Row of Cards
                        LazyVerticalGrid(
                            modifier = Modifier
                                .padding(top = 200.dp)
                                .scrollEnabled(false),
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            items(10) {
                                Column(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)) {
                                    ShimmerEffect(
                                        modifier = Modifier
                                            .height(180.dp)
                                            .fillMaxWidth()
                                            .padding(horizontal = 2.dp)
                                            .background(Color.LightGray, RoundedCornerShape(16)),
                                        durationMillis = 1000
                                    )
                                    ShimmerEffect(
                                        modifier = Modifier
                                            .height(40.dp)
                                            .fillMaxWidth()
                                            .padding(horizontal = 2.dp, vertical = 10.dp)
                                            .background(Color.LightGray, RoundedCornerShape(16)),
                                        durationMillis = 1000
                                    )
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        ShimmerEffect(
                                            modifier = Modifier
                                                .height(22.dp)
                                                .width(70.dp)
                                                .padding(horizontal = 2.dp)
                                                .background(Color.LightGray, RoundedCornerShape(16)),
                                            durationMillis = 1000
                                        )
                                        Spacer(modifier = Modifier
                                            .weight(1f)
                                            .height(22.dp))
                                        ShimmerEffect(
                                            modifier = Modifier
                                                .height(22.dp)
                                                .width(70.dp)
                                                .padding(horizontal = 2.dp)
                                                .background(Color.LightGray, RoundedCornerShape(16)),
                                            durationMillis = 1000
                                        )
                                    }
                                    ShimmerEffect(
                                        modifier = Modifier
                                            .height(50.dp)
                                            .fillMaxWidth()
                                            .padding(horizontal = 2.dp, vertical = 12.dp)
                                            .background(Color.LightGray, RoundedCornerShape(24)),
                                        durationMillis = 1000
                                    )
                                }
                            }
                        }
                        Box(
                            Modifier
                                .align(Alignment.TopCenter)
                                .fillMaxWidth()
                                .height(200.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Column(modifier = Modifier
                                .padding(top = 20.dp)) {
                                TopBar(screenTitle = if((booksState as BooksViewState.LoadingState).lang is LangResultDomain.Ru){"Литература"}else{"Books"}, onBackPressed = { backDispatcher.onBackPressed() })
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp)
                                        .padding(start = 20.dp, bottom = 8.dp, top = 8.dp),
                                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                                ) {
                                    repeat(6) {
                                        ShimmerEffect(
                                            modifier = Modifier
                                                .size(width = 120.dp, height = 38.dp)
                                                .padding(start = 2.dp, end = 2.dp)
                                                .background(Color.LightGray, RoundedCornerShape(34)),
                                            durationMillis = 1000
                                        )
                                    }
                                }
                                ShimmerEffect(
                                    modifier = Modifier
                                        .height(46.dp)
                                        .fillMaxWidth()
                                        .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                                        .background(Color.LightGray, RoundedCornerShape(34)),
                                    durationMillis = 1000
                                )
                            }
                        }
                    }
                }

                is BooksViewState.ErrorState -> {
                    Log.d("books in state is", "Error")
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(top = 40.dp)
                    ) {
                        TopBar(
                            screenTitle = if ((booksState as BooksViewState.ErrorState).lang is LangResultDomain.Ru) {
                                "Литература"
                            } else {
                                "Books"
                            }, onBackPressed = { backDispatcher.onBackPressed() })
                        Column(modifier = Modifier.align(Alignment.Center)) {
                            val bugBitmap = ImageBitmap.imageResource(id = R.drawable.bug_icon)
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(100.dp, 10.dp),
                                bitmap = bugBitmap,
                                contentDescription = "FoolStack"
                            )
                            Title(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = if ((booksState as BooksViewState.ErrorState).lang is LangResultDomain.Ru) {
                                    "Что-то пошло не так..."
                                } else {
                                    "Something went wrong..."
                                }
                            )
                            YellowButton(
                                onClick = { booksViewModel.refresh() },
                                text = if ((booksState as BooksViewState.ErrorState).lang is LangResultDomain.Ru) {
                                    "Обновить"
                                } else {
                                    "Refresh"
                                },
                                isEnabled = true,
                                isLoading = false,
                                modifier = Modifier
                                    .padding(top = 30.dp)
                            )
                        }
                    }
                }

                is BooksViewState.SuccessState -> {
                    Log.d("event in state is", "Success")
                    val successState = (booksState as BooksViewState.SuccessState)
                    if (successState.books?.books?.isNotEmpty() == true) {
                        val booksSubscribeFullText = if(successState.lang is LangResultDomain.Ru){
                            successState.books.subscribeText.replace("***", "${successState.books.subscribeMinCost} ₽")
                        } else{
                            successState.books.subscribeText.replace("***", "${successState.books.subscribeMinCost} \$")}
                        BooksVerticalSlider(
                            lang = if (successState.lang is LangResultDomain.Ru) { Lang.RU } else { Lang.ENG },
                            books = Mapper().mapToBookItems(successState.books),
                            chips = Mapper().mapToChips(successState.books.books ?: listOf()),
                            selectedChips = successState.selectedFilters,
                            searchKeyWord = searchKeyWord,
                            onBackPressed = { backDispatcher.onBackPressed() },
                            onRefresh = {  onRefresh() },
                            isRefreshing = isRefreshing,
                            onClickBook = {
                                booksViewModel.navigateToBook(
                                    navController = navController,
                                    bookId = bookId.value,
                                    bookDestination = bookDestination,
                                    prText = successState.books.prText,
                                    maxSalePercent = successState.books.maxSalePercent,
                                    bookSubscribeText = successState.books.subscribeText,
                                    bookSubscribeMinCost = successState.books.subscribeMinCost,
                                    bookSubscribeLink = successState.books.subscribeLink
                                )
                            },
                            selectId = bookId,
                            selectedChip = selectedFilter,
                            onclickChip = { booksViewModel.updateFilters(selectedFilter.value) },
                            onclickBuy = { booksViewModel.onClickLink(successState.books.books.find { it.bookId == bookId.value }?.bookRefLink?: "")},
                            subscribeText = booksSubscribeFullText,
                            subscribeClick = { booksViewModel.onClickLink(successState.books.subscribeLink) })
                    } else {
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .fillMaxSize()
                                .padding(top = 40.dp)
                        ) {

                            TopBar(
                                screenTitle = if (successState.lang is LangResultDomain.Ru) {
                                    "Литература"
                                } else {
                                    "Books"
                                }, onBackPressed = { backDispatcher.onBackPressed() })
                            val emptyText = if (successState.lang is LangResultDomain.Ru) {
                                "В данный момент нет\nподходящей литературы"
                            } else {
                                "There is no suitable literature\nat the moment"
                            }
                            Column(modifier = Modifier.align(Alignment.Center)) {
                                NotificationTitle(
                                    text = emptyText,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                                YellowButton(
                                    onClick = { onRefresh() },
                                    text = if (successState.lang is LangResultDomain.Ru) {
                                        "Обновить"
                                    } else {
                                        "Refresh"
                                    },
                                    isEnabled = true,
                                    isLoading = false,
                                    modifier = Modifier
                                        .padding(top = 30.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        else -> {
            Log.d("books realy complete", "no")
            booksViewModel.initViewModel()
        }
    }
}