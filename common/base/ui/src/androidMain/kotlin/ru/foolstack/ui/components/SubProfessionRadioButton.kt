package ru.foolstack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.theme.DisabledButtonTextColor
import ru.foolstack.ui.theme.DisabledColor
import ru.foolstack.ui.theme.HintText
import ru.foolstack.ui.theme.InputText
import ru.foolstack.ui.theme.ServiceBackground
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun SubProfessionRadioButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    text: String,
    onClick: ()-> Unit
) {
    val shape = RoundedCornerShape(8.dp)
    val background = if(isSelected){
        MaterialTheme.colorScheme.ServiceBackground
    }
    else{
        MaterialTheme.colorScheme.background
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .border(1.dp, MaterialTheme.colorScheme.HintText, shape)
            .background(background)
            .clickable { onClick() }

    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.InputText,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.Medium,
            style = TextStyle(
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.InputText,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }

}