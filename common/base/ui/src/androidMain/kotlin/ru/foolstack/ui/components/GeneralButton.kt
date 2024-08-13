package ru.foolstack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.ext.bounceClick
import ru.foolstack.ui.theme.DisabledButtonContent
import ru.foolstack.ui.theme.EnabledButtonContent
import ru.foolstack.ui.theme.EnabledButtonFirstBackground
import ru.foolstack.ui.theme.EnabledButtonSecondBackground
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun GeneralButton(
    text: String,
    onClickLogin: () -> Unit,
) {
    val shape = RoundedCornerShape(5.dp)
    val gradient = Brush.verticalGradient(listOf(MaterialTheme.colorScheme.EnabledButtonFirstBackground, MaterialTheme.colorScheme.EnabledButtonSecondBackground))
    val modifier = Modifier
        .background(Color.Transparent)
        .padding(horizontal = 16.dp)
        .clip(shape)
        .bounceClick(onClickLogin)
        .fillMaxWidth()
    Button(
        modifier = modifier
            .padding(horizontal = 0.dp)
            .then(modifier),
        shape = shape,
        colors = ButtonColors(containerColor = Color.Transparent, contentColor = MaterialTheme.colorScheme.EnabledButtonContent, disabledContainerColor = Color.Transparent, disabledContentColor = MaterialTheme.colorScheme.DisabledButtonContent),
        onClick = { onClickLogin() },
    ) {
        Box(
            modifier = modifier
                .background(gradient)
                .fillMaxWidth()
                .then(modifier),
            contentAlignment = Alignment.Center
        ) {
            Text(modifier = modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .then(modifier),
                text = text,
                textAlign = TextAlign.Center,
                fontFamily = montserratFamily, fontWeight = FontWeight.Medium, style = TextStyle(
                    fontSize = 16.sp)
            )
        }
    }
}