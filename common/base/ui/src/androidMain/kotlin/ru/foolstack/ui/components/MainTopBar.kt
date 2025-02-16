package ru.foolstack.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import ru.foolstack.ui.theme.MainBlack
import ru.foolstack.ui.theme.PrimaryTitleColor
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun MainTopBar(theme: String, userName: String = "", userType: UserType, lang: Lang, onClickSettings: ()-> Unit, onClickLogout: ()-> Unit){
    var showedUserName = userName
    if(userType==UserType.GUEST){
        showedUserName = if(lang==Lang.RU){ "Гость" } else{ "Guest" }
    }
    else {
        showedUserName = if(userType==UserType.UNKNOWN){
            "Unknown"
        } else{
            if(showedUserName.isNotEmpty() && showedUserName!="No-name" && showedUserName!="Ноунейм"){ userName } else{
                if(lang==Lang.RU){
                    "Ноунейм"
                } else{
                    "No-name"
                }
            }
        }
    }
    var isDropDown by remember { mutableStateOf(false) }
    FoolStackTheme(theme = theme) {
        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp) .fillMaxWidth()) {
            if(showedUserName!= "Unknown"){
                Text(modifier = Modifier
                    .clickable {
                        isDropDown = true
                    },
                    text = showedUserName,
                    color = MaterialTheme.colorScheme.MainBlack,
                    textAlign = TextAlign.Center,
                    fontFamily = montserratFamily, fontWeight = FontWeight.Bold, style = TextStyle(
                        fontSize = 20.sp)
                )
                DropdownMenu(
                    modifier = Modifier.background(MaterialTheme.colorScheme.MainBackground)
                        .wrapContentWidth(),
                    expanded = isDropDown,
                    onDismissRequest = { isDropDown = false }
                ) {
                    DropdownMenuItem(
                        text = {  CardText(text = if(lang==Lang.RU){ "Выйти" } else{ "Logout" }, modifier = Modifier)},
                        onClick = { onClickLogout()}
                    )
                }
            }
            else{
                ShimmerEffect(
                    modifier = Modifier
                        .width(116.dp)
                        .height(24.dp)
                        .background(Color.LightGray, shape = CutCornerShape(10)),
                    durationMillis = 1000
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(R.drawable.settings_icon),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.MainBlack,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onClickSettings() }
            )
        }
    }
}

enum class UserType {
    CLIENT, GUEST, UNKNOWN
}
