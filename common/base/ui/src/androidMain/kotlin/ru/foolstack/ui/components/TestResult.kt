package ru.foolstack.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.theme.MainBlack
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun TestResult(startText: String, endText: String, resultText:String, buttonText: String, onClick:()->Unit, modifier: Modifier){
    val annotatedString = buildAnnotatedString {
        append(startText)
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(resultText)
        }
        append(endText)
    }
    Column(modifier = modifier) {
        Text(text = annotatedString, fontFamily = montserratFamily, fontWeight = FontWeight.Normal, style = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.MainBlack,
            textAlign = TextAlign.Start
        )
        )
        Spacer(modifier = Modifier.weight(1F))
        YellowButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            text = buttonText,
            onClick = { onClick() },
            isEnabled = true,
            isLoading = false
        )
    }
}