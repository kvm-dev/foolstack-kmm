package ru.foolstack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.theme.ProfessionsBannerColor1
import ru.foolstack.ui.theme.ProfessionsBannerColor2
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun ProfessionsSaleBanner(text: String, modifier: Modifier, onClick: () -> Unit){
    var clickEnabled by remember { mutableStateOf(true) }
    val gradientBackground = Brush.linearGradient(listOf(MaterialTheme.colorScheme.ProfessionsBannerColor1, MaterialTheme.colorScheme.ProfessionsBannerColor2))
    val shape = RoundedCornerShape(10.dp)
    Box(modifier = modifier
        .clip(shape)
        .background(
            brush = gradientBackground,
            shape = shape
        )
        .clickable(enabled = clickEnabled) {
            clickEnabled = false
            onClick()
        }) {
        Text(modifier = Modifier.padding(30.dp),
            text = text,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        )
    }
}