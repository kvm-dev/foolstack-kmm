package ru.foolstack.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.theme.BaseText
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun BaseText(text: String){
    Text(modifier = Modifier.padding(vertical = 12.dp, horizontal = 38.dp), text =text, fontFamily = montserratFamily, fontWeight = FontWeight.Normal, style = TextStyle(
        fontSize = 17.sp,
        color = MaterialTheme.colorScheme.BaseText,
        textAlign = TextAlign.Center
    )
    )
}