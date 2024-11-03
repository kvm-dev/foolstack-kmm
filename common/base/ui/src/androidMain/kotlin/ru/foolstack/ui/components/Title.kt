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
import ru.foolstack.ui.theme.PrimaryTitleColor
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun Title(text: String, modifier: Modifier){
    Text(modifier = modifier.padding(bottom = 10.dp, start = 16.dp, end = 16.dp),
        text =text, fontFamily = montserratFamily, fontWeight = FontWeight.Normal, style = TextStyle(
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.PrimaryTitleColor,
            textAlign = TextAlign.Center
        )
    )
}