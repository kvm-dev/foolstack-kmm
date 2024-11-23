package ru.foolstack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.R
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.theme.ServiceBackground
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun CommentButtonWithText(modifier: Modifier, onClick: () -> Unit, lang: Lang) {
    val shape = RoundedCornerShape(8.dp)
    Row(modifier = modifier
        .clip(shape)
        .clickable {
        onClick()
    }){

        Box(
            modifier = Modifier.size(24.dp)
                .align(Alignment.CenterVertically)
                .clip(shape)
                .background(MaterialTheme.colorScheme.ServiceBackground),
        ) {
            // Inner content including an icon and a text label
            Icon(
                painter = painterResource(R.drawable.comment_icon),
                contentDescription = "Comment",
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .padding(4.dp)
                    .align(Alignment.Center)
            )
        }
        Text(
            modifier = modifier.align(Alignment.CenterVertically)
                .padding(start = 12.dp),
            text = if(lang == Lang.RU){"Оставить комментарий"} else{"Send comment"},
            fontFamily = montserratFamily, fontWeight = FontWeight.SemiBold, style = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.ServiceBackground,
                textAlign = TextAlign.Start
            )
        )
    }
}