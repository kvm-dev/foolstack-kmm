package ru.foolstack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.ext.bounceClick
import ru.foolstack.ui.theme.EnabledButtonFirstBackground
import ru.foolstack.ui.theme.EnabledButtonSecondBackground
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun SplashGuestButton(
    text: String,
    onClickGuest: () -> Unit = { },
) {
    val shape = RoundedCornerShape(5.dp)
    val modifier = Modifier
        .background(Color.Transparent)
        .padding(horizontal = 16.dp)
        .fillMaxWidth()
        .bounceClick(onClickGuest)

    Button(
        modifier = modifier
            .wrapContentWidth()
            .padding(vertical = 10.dp),
        shape = shape,
        colors = ButtonColors(containerColor = Color.Transparent, contentColor = MaterialTheme.colorScheme.EnabledButtonFirstBackground, disabledContainerColor = Color.Transparent, disabledContentColor = MaterialTheme.colorScheme.EnabledButtonSecondBackground),
        onClick = { onClickGuest() },
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .then(modifier),
            contentAlignment = Alignment.Center,
        ) {
            Text(modifier = modifier
                .padding(horizontal = 16.dp)
                .then(modifier),
                text = text,
                textAlign = TextAlign.Center,
                fontFamily = montserratFamily, fontWeight = FontWeight.Medium, style = TextStyle(
                    fontSize = 22.sp)
            )
        }
    }
}