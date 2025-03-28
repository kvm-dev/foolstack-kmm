package ru.foolstack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.theme.ServiceBackground
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun StudyAdditional(text: String, modifier: Modifier){
    val shape = RoundedCornerShape(bottomStart = 10.dp, topEnd = 10.dp)
    Box(modifier = modifier
        .clip(shape)
        .background(
            color = MaterialTheme.colorScheme.ServiceBackground,
            shape = shape
        )) {
        Text(modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
            text =text, fontFamily = montserratFamily, fontWeight = FontWeight.Bold, style = TextStyle(
                fontSize = 12.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        )
    }
}