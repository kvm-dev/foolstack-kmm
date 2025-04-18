package ru.foolstack.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.theme.MainWhite
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun TestsTitle(text: String, modifier: Modifier){
    Text(
        modifier = modifier,
        text = text, fontFamily = montserratFamily, fontWeight = FontWeight.Black, style = TextStyle(
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.MainWhite,
            textAlign = TextAlign.Start,
        )
    )
}