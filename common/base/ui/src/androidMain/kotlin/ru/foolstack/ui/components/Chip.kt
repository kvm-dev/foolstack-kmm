package ru.foolstack.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.shape.RoundedCornerShape
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
import ru.foolstack.ui.theme.SelectedChipBackground
import ru.foolstack.ui.theme.UnselectedChipBackground
import ru.foolstack.ui.theme.UnselectedChipStroke
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun Chip(
    id: Int,
    label: String,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.SelectedChipBackground
    } else {
        MaterialTheme.colorScheme.UnselectedChipBackground
    }
    val borderColor = if (isSelected) {
        Color.Transparent
    } else {
        MaterialTheme.colorScheme.UnselectedChipStroke
    }
    Box(
        modifier = modifier
            .border(border = BorderStroke(1.dp, borderColor), shape =  RoundedCornerShape(percent = 34))
            .clip(shape = RoundedCornerShape(percent = 34))
            .background(backgroundColor)
            .clickable(onClick = {onClick()})

    ) {
        Text(
            text = label,
            color = Color.Black,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.Black,
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp)
        )
    }
}