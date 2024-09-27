package ru.foolstack.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.R
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.theme.FoolStackTheme
import ru.foolstack.ui.theme.MainBackground
import ru.foolstack.ui.theme.PrimaryTitleColor
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun MainTopBar(userName: String = "", userType: UserType, lang: Lang){
    var showedUserName = userName
    showedUserName = if(userType==UserType.GUEST){
        if(lang==Lang.RU){ "Гость" } else{ "Guest" }
    }
    else {
        if (showedUserName.isNotEmpty()){ userName } else{
            if(lang==Lang.RU){
                "Твое имя"
            } else{
                "Your name"
            }
        }
    }
    FoolStackTheme {
        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp) .fillMaxWidth()) {
            Text(text = showedUserName,
                color = MaterialTheme.colorScheme.PrimaryTitleColor,
                textAlign = TextAlign.Center,
                fontFamily = montserratFamily, fontWeight = FontWeight.Bold, style = TextStyle(
                    fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painterResource(R.drawable.settings_icon),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

enum class UserType {
    CLIENT, GUEST
}
