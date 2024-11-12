package ru.foolstack.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.theme.PrimaryTitleColor
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun ServiceSmallTitle(text: String, modifier: Modifier){
    Text(
        modifier = modifier.
        height(40.dp),
        maxLines = 3,
        text =text,
        fontFamily = montserratFamily,
        fontWeight = FontWeight.Black,
        style = TextStyle(
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.PrimaryTitleColor,
            textAlign = TextAlign.Start,
            fontStyle = FontStyle.Italic
        )
    )
}