package ru.foolstack.ui.components

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.theme.SalePrice
import ru.foolstack.ui.theme.montserratFamily

@SuppressLint("SuspiciousIndentation")
@Composable
fun SalePrice(text: String, lang: Lang){

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

        Text(
            maxLines = 1,
            text = cost,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.End,
            style = TextStyle(
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.SalePrice,
                textAlign = TextAlign.Start,
                fontStyle = FontStyle.Italic
            )
        )

}