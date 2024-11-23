package ru.foolstack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.foolstack.ui.theme.DisabledButtonTextColor
import ru.foolstack.ui.theme.DisabledColor
import ru.foolstack.ui.theme.HintText
import ru.foolstack.ui.theme.InputText

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    placeholder: String,
    text: MutableState<String>,
    onValueChange: (String) -> Unit
) {
    val shape = RoundedCornerShape(10.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 16.dp)
            .clip(shape)
            .border(1.dp, MaterialTheme.colorScheme.HintText, shape)
            .background(MaterialTheme.colorScheme.background)

    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text.value,
            onValueChange = onValueChange,
            singleLine = false,
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
            placeholder = {
                HintText(text = placeholder,
                modifier = Modifier
            ) }
        )
    }
}