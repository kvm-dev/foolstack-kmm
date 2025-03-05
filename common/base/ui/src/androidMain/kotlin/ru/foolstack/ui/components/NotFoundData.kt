package ru.foolstack.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.R
import ru.foolstack.ui.theme.BaseText
import ru.foolstack.ui.theme.MainBlack
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun NotFoundData(titleText: String, descriptionText: String){
    Column(modifier = Modifier.
    padding(top = 20.dp)) {
        TopBar(screenTitle = titleText,
            action = {},
            isIconVisible = false,
            isBackArrow = false
        )
        Image(
            modifier = Modifier
                .padding(top = 40.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
            painter = painterResource(R.drawable.data_not_found),
            contentDescription = "FoolStack"
        )

        Text(modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 20.dp),
            text = descriptionText, fontFamily = montserratFamily,
            fontWeight = FontWeight.Medium, style = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.BaseText,
                textAlign = TextAlign.Center,
            )
        )
    }
}