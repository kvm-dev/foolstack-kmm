package ru.foolstack.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.theme.CardText
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun BookSaleText(text: String, salePrice: Int, lang: Lang, modifier: Modifier){
    val maxPercentSale = if(lang == Lang.RU){ "со скидкой до $salePrice%" }else{ "with discount up to $salePrice%" }
    Column(modifier = modifier){
        Text(
            modifier = modifier.align(Alignment.CenterHorizontally),
            text = text, fontFamily = montserratFamily, fontWeight = FontWeight.ExtraLight, style = TextStyle(
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.CardText,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic
            )
        )
        Text(
            modifier = modifier,
            text = maxPercentSale, fontFamily = montserratFamily, fontWeight = FontWeight.SemiBold, style = TextStyle(
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.CardText,
                textAlign = TextAlign.Start,
                fontStyle = FontStyle.Italic
            )
        )
    }

}