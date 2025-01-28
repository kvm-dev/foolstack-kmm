package ru.foolstack.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ru.foolstack.ui.R
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.theme.MainBlack
import ru.foolstack.ui.theme.PrimaryTitleColor
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun TestCardTimer(lang:Lang, timerValue: Int, modifier: Modifier, onStop: ()->Unit){
    var timer by remember { mutableIntStateOf(timerValue) }
    LaunchedEffect(key1 = timer) {
        if (timer > 0) {
            delay(1000L)
            timer -= 1
        }
        else{
            //останавливаем тестирование
            onStop()
        }
    }
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 10.dp)) {
        var type = if(lang == Lang.RU){" сек"} else {" sec" }

        Icon(
            painter = painterResource(id = R.drawable.timer_bomb_icon),
            contentDescription = "Timer",
            modifier = Modifier
                .align(Alignment.CenterVertically),
            tint = MaterialTheme.colorScheme.MainBlack,
        )

        Text(
            modifier = modifier
                .padding(start = 4.dp)
                .align(Alignment.CenterVertically),
            text = if(lang == Lang.RU ){"Осталось времени: "} else {"Time left: "},
            color = MaterialTheme.colorScheme.PrimaryTitleColor,
            textAlign = TextAlign.Start,
            fontFamily = montserratFamily, fontWeight = FontWeight.Medium, style = TextStyle(
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic)
        )
        val valueString = "$timer $type"

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 2.dp)
                .align(Alignment.CenterVertically),
            text = valueString ,
            color = MaterialTheme.colorScheme.PrimaryTitleColor,
            textAlign = TextAlign.Start,
            fontFamily = montserratFamily, fontWeight = FontWeight.SemiBold, style = TextStyle(
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic)
        )
    }
}