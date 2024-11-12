package ru.foolstack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.foolstack.ui.theme.BannerBackground
import ru.foolstack.ui.theme.InputText

@Composable
fun BookPrBlock(text: String, modifier: Modifier){
    val shape = RoundedCornerShape(18.dp)
    Box(modifier = modifier
        .clip(shape)
        .border(1.dp, MaterialTheme.colorScheme.InputText, shape)
        .background(
            color = MaterialTheme.colorScheme.BannerBackground,
            shape = shape
        )) {
        ServiceBlackItalic(modifier = Modifier
            .align(Alignment.Center)
            .padding(horizontal = 10.dp, vertical = 12.dp),
            text = text)
    }
}