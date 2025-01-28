package ru.foolstack.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.theme.DisabledColor
import ru.foolstack.ui.theme.EnabledButtonSecondContentColor
import ru.foolstack.ui.theme.GreenButtonContainer
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun SecondGreenButton(
    text: String,
    onClick: () -> Unit = { },
    isEnabled: Boolean
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        onClick = { onClick() },
        enabled = isEnabled,
        content = { Text(modifier = Modifier
            .padding(vertical = 2.dp, horizontal = 8.dp),
            text = text,
            textAlign = TextAlign.Center,
            fontFamily = montserratFamily, fontWeight = FontWeight.SemiBold, style = TextStyle(
                fontSize = 16.sp)
        ) },
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.GreenButtonContainer,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.DisabledColor,
            containerColor = Color.Transparent
        )
    )
}