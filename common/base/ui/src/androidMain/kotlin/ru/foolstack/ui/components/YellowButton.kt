package ru.foolstack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.ext.bounceClick
import ru.foolstack.ui.theme.DisabledButtonContent
import ru.foolstack.ui.theme.DisabledButtonTextColor
import ru.foolstack.ui.theme.DisabledColor
import ru.foolstack.ui.theme.EnabledButtonContent
import ru.foolstack.ui.theme.MainYellow
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun YellowButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean,
    isLoading: Boolean
) {
    val shape = RoundedCornerShape(10.dp)
    if (!isLoading) {
        Button(
            modifier = modifier,
            shape = shape,
            onClick = { onClick() },
            enabled = isEnabled,
            content = { Text(modifier = Modifier
                    .padding(vertical = 14.dp, horizontal = 8.dp),
                    text = text,
                    textAlign = TextAlign.Center,
                    fontFamily = montserratFamily, fontWeight = FontWeight.Black, style = TextStyle(
                        fontSize = 14.sp)
                ) },
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.EnabledButtonContent,
                disabledContainerColor = MaterialTheme.colorScheme.DisabledColor,
                disabledContentColor = MaterialTheme.colorScheme.DisabledButtonContent,
                containerColor = MaterialTheme.colorScheme.MainYellow
            )
        )
    }
    else{
        LoadingIndicator()
    }
}
