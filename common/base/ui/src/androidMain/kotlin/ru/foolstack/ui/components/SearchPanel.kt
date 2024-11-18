package ru.foolstack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.theme.DisabledButtonTextColor
import ru.foolstack.ui.theme.DisabledColor
import ru.foolstack.ui.theme.HintText
import ru.foolstack.ui.theme.InputText

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