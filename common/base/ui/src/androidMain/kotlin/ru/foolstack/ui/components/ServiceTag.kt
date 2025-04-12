package ru.foolstack.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.theme.ServiceText
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun ServiceTag(text: String){
    Text(
        text = text,
        fontFamily = montserratFamily,
        fontWeight = FontWeight.Bold,
        style = TextStyle(
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.ServiceText,
            textAlign = TextAlign.Start
        ),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}