package ru.foolstack.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.R
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.theme.HintText
import ru.foolstack.ui.theme.InputText
import ru.foolstack.ui.theme.MainDarkGreen
import ru.foolstack.ui.theme.montserratFamily
import ru.foolstack.ui.utils.removeHtmlTags

@Composable
fun MaterialSection(materialHeader: String,
                    materialText: String, isExpanded: Boolean,
                    onHeaderClicked: () -> Unit,
                    onDetailsClick: ()-> Unit,
                    sendCommentClick: ()-> Unit,
                    lang: Lang) {
    //replace html tags
    val withoutHtmlText = materialText.removeHtmlTags()

    var answerText = withoutHtmlText

    if(answerText.length>100){
        answerText = answerText.substring(0, 100).plus("...")
    }
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
        Row(modifier = Modifier
            .clickable { onHeaderClicked() }
            .background(MaterialTheme.colorScheme.background)
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .border(
                1.dp,
                MaterialTheme.colorScheme.HintText,
                RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
            )) {
            Text(
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 16.dp)
                    .weight(1.0f),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.InputText,
                    textAlign = TextAlign.Start,
                    fontStyle = FontStyle.Italic
                ),
                text = materialHeader,
                fontFamily = montserratFamily, fontWeight = FontWeight.Normal,
            )
            val icon: Painter = if (isExpanded) {
                painterResource(id = R.drawable.arrow_up_icon)
            } else {
                painterResource(id = R.drawable.arrow_down_icon)
            }
            Icon(modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 8.dp),
                painter = icon, contentDescription = null)
        }
        if(isExpanded){
            Column(modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.HintText,
                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                )
                .padding(top = 12.dp)
            ) {
                Text(modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.InputText,
                        textAlign = TextAlign.Start,
                        fontStyle = FontStyle.Italic
                    ),
                    text = answerText,
                    fontFamily = montserratFamily, fontWeight = FontWeight.Normal,)

                Row(modifier = Modifier
                    .padding(top = 12.dp, bottom = 22.dp, start = 16.dp, end = 16.dp)) {
                    CommentButton(modifier = Modifier, onClick = sendCommentClick)
                    Spacer(modifier = Modifier.weight(1f))
                if(withoutHtmlText.length>100){
                    Text(modifier = Modifier
                        .clip(RoundedCornerShape(2.dp))
                        .clickable { onDetailsClick() },
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.MainDarkGreen,
                            textAlign = TextAlign.End,
                        ),
                        text = if(lang == Lang.RU){ "Подробнее" } else { "More" },
                        fontFamily = montserratFamily, fontWeight = FontWeight.SemiBold)
                }
                }
            }

        }
    }
}