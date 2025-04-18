package ru.foolstack.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.R
import ru.foolstack.ui.theme.MainBlack
import ru.foolstack.ui.theme.PrimaryTitleColor
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun TopBar(
    screenTitle: String,
    action:  () -> Unit,
    isDark: Boolean = true,
    isTitleVisible: Boolean = true,
    isIconVisible: Boolean = true,
    isBackArrow: Boolean = true){
    var clickBackEnabled by remember { mutableStateOf(true) }
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp).fillMaxWidth()) {
            val icon: Painter
            if (isIconVisible) {
                icon = if (isDark) {
                    if(isBackArrow){
                        painterResource(R.drawable.back_arrow_icon)
                    } else{
                        painterResource(R.drawable.filter_icon)
                    }
                } else {
                    if(isBackArrow){
                        painterResource(R.drawable.back_arrow_white_icon)
                    } else{
                        painterResource(R.drawable.filter_icon_white)
                    }
                }
                Icon(
                    painter = icon,
                    contentDescription = "",
                    tint =  if (isDark){ MaterialTheme.colorScheme.MainBlack } else { Color.White },
                    modifier = Modifier.size(24.dp)
                        .clickable(enabled = clickBackEnabled) {
                            clickBackEnabled = false
                            action()
                        }
                )
        }
            if(isTitleVisible){
                Text(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).align(Alignment.Center), text = screenTitle,
                    color = MaterialTheme.colorScheme.MainBlack,
                    textAlign = TextAlign.Center,
                    fontFamily = montserratFamily, fontWeight = FontWeight.Black, style = TextStyle(
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic)
                )
            }
        }
}