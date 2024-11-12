package ru.foolstack.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.theme.DisabledButtonContent
import ru.foolstack.ui.theme.DisabledColor
import ru.foolstack.ui.theme.GreenButtonContainer
import ru.foolstack.ui.theme.GreenButtonContent
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun GreenButtonLittle(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean,
    isLoading: Boolean,
) {
    val shape = RoundedCornerShape(16.dp)
    if (!isLoading) {
        Button(
            modifier = modifier
                .fillMaxWidth(),
            shape = shape,
            onClick = { onClick() },
            enabled = isEnabled,
            content = {
                Text(modifier = Modifier
                .padding(horizontal = 8.dp),
                text = text,
                textAlign = TextAlign.Center,
                fontFamily = montserratFamily, fontWeight = FontWeight.Black, style = TextStyle(
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic)
            ) },
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.GreenButtonContent,
                disabledContainerColor = MaterialTheme.colorScheme.DisabledColor,
                disabledContentColor = MaterialTheme.colorScheme.DisabledButtonContent,
                containerColor = MaterialTheme.colorScheme.GreenButtonContainer
            )
        )
    }
    else{
        LoadingIndicator()
    }
}
