package ru.foolstack.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
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
import ru.foolstack.ui.theme.Divider
import ru.foolstack.ui.theme.PrimaryTitleColor
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun TestCardQuestion(text: String, modifier: Modifier){
    Column(modifier = modifier
            .fillMaxWidth()
        .padding(start = 20.dp, end = 20.dp, top = 40.dp, bottom = 16.dp)) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            text = text,
            color = MaterialTheme.colorScheme.PrimaryTitleColor,
            textAlign = TextAlign.Center,
            fontFamily = montserratFamily, fontWeight = FontWeight.Bold, style = TextStyle(
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic)
        )
        HorizontalDivider(modifier = Modifier, thickness = 1.dp, color = MaterialTheme.colorScheme.Divider)
    }
}