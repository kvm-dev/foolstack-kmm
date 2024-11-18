package ru.foolstack.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import ru.foolstack.ui.ext.header
import ru.foolstack.ui.model.BookItem
import ru.foolstack.ui.model.Chip
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.theme.LoadingIndicatorBackground
import ru.foolstack.ui.theme.MainYellow
import ru.foolstack.ui.utils.decodeBase64ToBitmap
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksVerticalSlider(
    lang: Lang,
    books: List<BookItem>,
    chips: List<Chip>,
    selectedChips: List<String>,
    searchKeyWord: MutableState<String>,
    onBackPressed: () -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    onClickBook: () -> Unit,
    selectId: MutableState<Int>,
    selectedChip: MutableState<String>,
    subscribeText: String,
    subscribeClick: ()-> Unit,
    onclickChip: () -> Unit,
    onclickBuy: () -> Unit,
) {
    val filteredBooks = HashSet<BookItem>()
    selectedChips.filter { it.isNotEmpty() }.forEach { chip ->
        books.forEach { book ->
            if (book.bookTags.contains(chip)) {
                filteredBooks.add(book)
            }
        }
    }

   val keywordFilteredBooks = if(searchKeyWord.value.isNotEmpty() && searchKeyWord.value.length>2){
        filteredBooks.filter { it.bookName.lowercase(Locale.ROOT).contains(searchKeyWord.value.lowercase(Locale.ROOT)) }.toList()
    }
    else{
        filteredBooks.toList()
    }
    val state = rememberPullToRefreshState()

    val scaleFraction = {
        if (isRefreshing) 1f
        else LinearOutSlowInEasing.transform(state.distanceFraction).coerceIn(0f, 1f)
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .pullToRefresh(
            state = state,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh
        )
        .padding(bottom = 20.dp)){
        Box(
            Modifier
                .align(Alignment.TopCenter)
                .graphicsLayer {
                    scaleX = scaleFraction()
                    scaleY = scaleFraction()
                }
                .zIndex(1F)
        ) {
            PullToRefreshDefaults.Indicator(state = state, isRefreshing = isRefreshing, color = MaterialTheme.colorScheme.MainYellow, containerColor = MaterialTheme.colorScheme.LoadingIndicatorBackground)
        }
        // Scrollable Row of Cards
        LazyVerticalGrid(
            modifier = Modifier
                .padding(top = 200.dp),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.Center
        ) {
            header{
                BookPrBlock(text = subscribeText, modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 20.dp)
                    .clickable {
                    subscribeClick()
                })
            }
            items(keywordFilteredBooks.size) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            selectId.value = keywordFilteredBooks[index].bookId
                            onClickBook()
                        }
                        .padding(horizontal = 20.dp, vertical = 18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    ) {
                        if (keywordFilteredBooks[index].bookImageBase64.isNotEmpty()) {
                            keywordFilteredBooks[index].bookImageBase64.decodeBase64ToBitmap()?.let {
                                Image(
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .height(180.dp)
                                        .padding(horizontal = 10.dp),
                                    bitmap = it,
                                    contentDescription = keywordFilteredBooks[index].bookName
                                )
                            }
                        } else {
                            Image(
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(180.dp)
                                    .padding(horizontal = 10.dp),
                                painter = painterResource(R.drawable.bug_icon),
                                contentDescription = keywordFilteredBooks[index].bookName
                            )
                        }
                        ServiceSmallTitle(
                            keywordFilteredBooks[index].bookName, modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp, start = 10.dp, end = 10.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            if (keywordFilteredBooks[index].bookSalePrice != 0) {
                                OriginalPrice(
                                    text = keywordFilteredBooks[index].bookPrice.toString(),
                                    lang = lang,
                                    isStrike = true
                                )
                            } else {
                                OriginalPrice(
                                    text = keywordFilteredBooks[index].bookPrice.toString(),
                                    lang = lang,
                                    isStrike = false
                                )
                            }
                            Spacer(
                                Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .background(Color.LightGray)
                            )
                            if (keywordFilteredBooks[index].bookSalePrice != 0) {
                                SalePrice(
                                    text = keywordFilteredBooks[index].bookSalePrice.toString(),
                                    lang = lang
                                )
                            }
                        }
                        GreenButtonLittle(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                            text = if (lang == Lang.RU) {
                                "Купить"
                            } else {
                                "Buy"
                            },
                            onClick = {
                                selectId.value = keywordFilteredBooks[index].bookId
                                onclickBuy()
                                      },
                            isEnabled = true,
                            isLoading = false
                        )
                    }
                }
            }
            header{
                BookPrBlock(text = subscribeText, modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 20.dp)
                    .clickable {
                    subscribeClick()
                })
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
                TopBar(screenTitle = if(lang == Lang.RU){"Литература"}else{"Books"}, onBackPressed = onBackPressed)
                ChipSelector(chips = chips, selectedChips = selectedChips, selectedChip = selectedChip, onclickChip = onclickChip)
                SearchPanel(modifier = Modifier
                    .fillMaxWidth(),
                    keyword = searchKeyWord,
                    lang = lang,
                    onValueChange = { searchKeyWord.value = it})
            }
        }
    }
}