package ru.foolstack.books.impl.presentation.ui

import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.foolstack.books.impl.presentation.viewmodel.BookCardViewModel
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.model.ProgressState
import ru.foolstack.ui.R
import ru.foolstack.ui.components.BookPrBlock
import ru.foolstack.ui.components.BookSaleText
import ru.foolstack.ui.components.CardBoldItalicTitle
import ru.foolstack.ui.components.CardText
import ru.foolstack.ui.components.GreenButton
import ru.foolstack.ui.components.Title
import ru.foolstack.ui.components.TopBar
import ru.foolstack.ui.components.YellowButton
import ru.foolstack.ui.model.Lang

@Composable
fun BookCardScreen(bookCardViewModel: BookCardViewModel = koinViewModel(), bookId: Int, prText: String, maxSalePercent: Int, bookSubscribeText: String, bookSubscribeMinCost: Int, bookSubscribeLink: String) {
    val backDispatcher =
        checkNotNull(LocalOnBackPressedDispatcherOwner.current).onBackPressedDispatcher
    val viewModelState by bookCardViewModel.progressState.collectAsState()
    when (viewModelState) {
        ProgressState.COMPLETED -> {
            Log.d("realy bookCard Complete", "yes")
            val bookState by bookCardViewModel.uiState.collectAsState()
            when (bookState) {
                is BookCardViewState.Idle -> {
                    Log.d("bookCard in state is", "Error")
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(top = 40.dp)
                    ) {
                        TopBar(
                            screenTitle = if ((bookState as BookCardViewState.Idle).lang is LangResultDomain.Ru) {
                                "Книга"
                            } else {
                                "Book"
                            }, action = { backDispatcher.onBackPressed() })
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
                                text = if ((bookState as BookCardViewState.Idle).lang is LangResultDomain.Ru) {
                                    "Что-то пошло не так..."
                                } else {
                                    "Something went wrong..."
                                }
                            )
                            YellowButton(
                                onClick = { backDispatcher.onBackPressed() },
                                text = if ((bookState as BookCardViewState.Idle).lang is LangResultDomain.Ru) {
                                    "Вернуться к литературе"
                                } else {
                                    "Return to books"
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

                is BookCardViewState.SuccessState -> {
                    Log.d("bookCard in state is", "Success")
                    val successState = (bookState as BookCardViewState.SuccessState)
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        Column(modifier = Modifier.align(Alignment.TopCenter)) {
                                    TopBar(
                                        screenTitle = "",
                                        action = { backDispatcher.onBackPressed() },
                                        isDark = true,
                                        isTitleVisible = false,
                                    )
                                    Column(modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .fillMaxWidth()
                                        .padding(top = 2.dp, start = 20.dp, end = 20.dp)) {
                                        CardBoldItalicTitle(text = successState.book?.bookName?:"",
                                            modifier = Modifier
                                                .padding(start = 60.dp, end = 60.dp, bottom = 10.dp)
                                                .align(Alignment.CenterHorizontally))
                                        Column(modifier = Modifier.
                                        verticalScroll(rememberScrollState())) {
                                            BookSaleText(
                                                text = prText,
                                                salePrice = maxSalePercent,
                                                lang = if(successState.lang is LangResultDomain.Ru){Lang.RU} else {Lang.ENG},
                                                modifier = Modifier
                                                    .padding(
                                                        start = 38.dp,
                                                        end = 38.dp,
                                                    )
                                                    .align(Alignment.CenterHorizontally))
                                            CardText(text = successState.book?.bookDescription?:"", modifier = Modifier.padding(top = 20.dp))
                                            val bookSubscribeFullText = if(successState.lang is LangResultDomain.Ru){
                                                bookSubscribeText.replace("***", "$bookSubscribeMinCost ₽")
                                            } else{
                                                bookSubscribeText.replace("***", "$bookSubscribeMinCost \$")}
                                            BookPrBlock(text = bookSubscribeFullText, modifier = Modifier
                                                .padding(top = 12.dp)
                                                .clickable { bookCardViewModel.onClickLink(bookSubscribeLink) })
                                            GreenButton(
                                                modifier = Modifier
                                                    .padding(top = 16.dp, bottom = 22.dp)
                                                    .fillMaxWidth(),
                                                text = if(successState.lang is LangResultDomain.Ru){
                                                    "Купить за ${successState.book?.bookCostWithSale?: 0} ₽" } else{ "Buy ${successState.book?.bookCostWithSale?: 0} \$" },
                                                onClick = { bookCardViewModel.onClickLink(successState.book?.bookRefLink?: "") },
                                                isEnabled = true,
                                                isLoading = false
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

        else -> {
            Log.d("bookCard realy complete", "no")
            bookCardViewModel.initViewModel(bookId = bookId)
        }
    }
}