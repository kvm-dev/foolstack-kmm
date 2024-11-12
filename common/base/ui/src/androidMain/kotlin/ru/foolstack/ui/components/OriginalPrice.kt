package ru.foolstack.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.theme.MainOrange
import ru.foolstack.ui.theme.MainYellow
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun OriginalPrice(text: String, lang: Lang, isStrike: Boolean = false){

    var cost = text
    if(cost.length>4){
        cost = cost.substring(0, 3)
    }
    var symbol = ""
    symbol = if (lang == Lang.RU) {
        "₽"
    } else {
        "$"
    }
    cost = "$cost $symbol"

    if(isStrike){
        Text(
            maxLines = 1,
            text = cost,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.Black,
            style = TextStyle(
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.MainYellow,
                textAlign = TextAlign.Start,
                textDecoration = TextDecoration.LineThrough,
                fontStyle = FontStyle.Italic
            )
        )
    }
    else{
        Text(
            maxLines = 1,
            text = cost,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.Black,
            style = TextStyle(
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.MainYellow,
                textAlign = TextAlign.Start,
                fontStyle = FontStyle.Italic
            )
        )
    }
}