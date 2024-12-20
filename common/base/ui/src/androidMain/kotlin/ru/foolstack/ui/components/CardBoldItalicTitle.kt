package ru.foolstack.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.theme.InputText
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun CardBoldItalicTitle(text: String, modifier: Modifier){
    Text(
        modifier = modifier,
        textAlign = TextAlign.Center,
        text =text, fontFamily = montserratFamily, fontWeight = FontWeight.Black, style = TextStyle(
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.InputText,
            textAlign = TextAlign.Start,
            fontStyle = FontStyle.Italic
        )
    )
}