package ru.foolstack.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.theme.DisabledButtonTextColor
import ru.foolstack.ui.theme.DisabledColor
import ru.foolstack.ui.theme.HintText
import ru.foolstack.ui.theme.InputText

//@OptIn(ExperimentalMaterial3Api::class)
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun SearchPanel() {
//    var text by remember { mutableStateOf("") } // Query for SearchBar
//    var active by remember { mutableStateOf(false) } // Active state for SearchBar
//
//    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
//        Text(
//            modifier = Modifier.fillMaxWidth(),
//            textAlign = TextAlign.Center,
//            text = "Search App Example"
//        )
//
////        val colors1 = SearchBarDefaults.colors()
//        SearchBar(
//            inputField = {
//                SearchBarDefaults.InputField(
//                    query = text,
//                    onQueryChange = {
//                        text = it
//                    },
//                    onSearch = {
//                        active = false
//                    },
//                    expanded = active,
//                    onExpandedChange = {  },
//                    enabled = true,
//                    placeholder = {
//                        Text(text = "Enter your query")
//                    },
//                    leadingIcon = {
//                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
//                    },
//                    trailingIcon = {
//                            Icon(
//                                modifier = Modifier.clickable {
//                                    if (text.isNotEmpty()) {
//                                        text = ""
//                                    } else {
//                                        active = false
//                                    }
//                                },
//                                imageVector = Icons.Default.Close,
//                                contentDescription = "Close icon"
//                            )
//
//                    },
//                    colors = TextFieldDefaults.colors(
//                        //setting the text field background when it is focused
//                        focusedContainerColor = Color.Green,
//                        focusedIndicatorColor = MaterialTheme.colorScheme.InputText,
//                        focusedTextColor = MaterialTheme.colorScheme.InputText,
//                        //setting the text field background when it is unfocused or initial state
//                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
//                        unfocusedIndicatorColor = MaterialTheme.colorScheme.InputText,
//                        unfocusedTextColor = MaterialTheme.colorScheme.InputText,
//                        //setting the text field background when it is disabled
//                        disabledContainerColor = MaterialTheme.colorScheme.DisabledColor,
//                    ),
//                    interactionSource = null,
//                )
//            },
//            expanded = active,
//            onExpandedChange = {},
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(54.dp),
//            shape = RoundedCornerShape(16.dp),
//            tonalElevation = SearchBarDefaults.TonalElevation,
//            shadowElevation = SearchBarDefaults.ShadowElevation,
//            windowInsets = SearchBarDefaults.windowInsets,
//            content = {}
//        )
//    }
//}

@Composable
fun SearchPanel(
    modifier: Modifier = Modifier,
    keyword: MutableState<String>,
    lang: Lang,
    onValueChange: (String) -> Unit
) {
    val shape = RoundedCornerShape(10.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(shape)
            .border(1.dp, MaterialTheme.colorScheme.HintText, shape)
            .background(MaterialTheme.colorScheme.background)

    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = keyword.value,
            onValueChange = onValueChange,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                focusedTextColor = MaterialTheme.colorScheme.InputText,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.InputText,
                focusedPlaceholderColor = MaterialTheme.colorScheme.HintText,
                focusedLeadingIconColor = MaterialTheme.colorScheme.InputText,
                focusedTrailingIconColor = MaterialTheme.colorScheme.InputText,

                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedTextColor = MaterialTheme.colorScheme.InputText,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.InputText,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.HintText,

                disabledTextColor = MaterialTheme.colorScheme.DisabledButtonTextColor,
                disabledContainerColor = MaterialTheme.colorScheme.DisabledColor
            ),
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "search") },
            trailingIcon = {
                Icon(imageVector = Icons.Default.Clear,
                    contentDescription = "close",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 10.dp)
                        .clickable {
                    keyword.value = ""
                }) },
            placeholder = { HintText(text = if (lang == Lang.RU ){ "Название книги"} else {"The name of book"},
                modifier = Modifier) }
        )
    }

}