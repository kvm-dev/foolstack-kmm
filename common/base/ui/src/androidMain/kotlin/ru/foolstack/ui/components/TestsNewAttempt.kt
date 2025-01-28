package ru.foolstack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.R
import ru.foolstack.ui.theme.GreenButtonContainer
import ru.foolstack.ui.theme.MainWhite
import ru.foolstack.ui.theme.montserratFamily
import ru.foolstack.ui.utils.timestampToDateString

@Composable
fun TestsLastAttempt(value: Long, text: String, modifier: Modifier){
    val date = value.timestampToDateString()
    Row(
        modifier = Modifier
            .padding(top = 10.dp),

    ) {
        Box(modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .size(20.dp)
            .background(MaterialTheme.colorScheme.GreenButtonContainer)){
            Icon(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Center),
                tint = MaterialTheme.colorScheme.MainWhite,
                painter = painterResource(R.drawable.locked_icon),
                contentDescription = "icon"
            )
        }
        Text(
            modifier = modifier
                .align(Alignment.CenterVertically)
                .padding(start = 6.dp),
            text = "$text ${value.timestampToDateString()}", fontFamily = montserratFamily, fontWeight = FontWeight.Medium, style = TextStyle(
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.MainWhite,
                textAlign = TextAlign.Start,
            )
        )
    }
}