package ru.foolstack.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.theme.ErrorColor
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 4,
    errorMessage: String,
    isError: Boolean,
    isEnabled: Boolean,
    onOtpTextChange: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    BasicTextField(
        enabled = isEnabled,
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = androidx.compose.ui.text.input.ImeAction.Done),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText,
                        isError = isError
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        },
    )
    if (isError){
        Text(
            text = errorMessage,
            fontFamily = montserratFamily, fontWeight = FontWeight.Normal,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.ErrorColor,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CharView(
    index: Int,
    text: String,
    isError: Boolean
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }
    val modifier: Modifier
    if(!isError){
        modifier = Modifier
            .wrapContentHeight(align = Alignment.CenterVertically)
            .width(50.dp)
            .height(50.dp)
            .border(
                1.dp, when {
                    isFocused -> Color.Black
                    else -> Color.Gray
                }, RoundedCornerShape(8.dp)
            )
            .padding(4.dp)
    }
    else{
        modifier = Modifier
            .wrapContentHeight(align = Alignment.CenterVertically)
            .width(50.dp)
            .height(50.dp)
            .border(
                1.dp, when {
                    isFocused -> MaterialTheme.colorScheme.ErrorColor
                    else -> MaterialTheme.colorScheme.ErrorColor
                }, RoundedCornerShape(8.dp)
            )
            .padding(4.dp)
    }
    Text(
        modifier = modifier,
        text = char,
        fontFamily = montserratFamily, fontWeight = FontWeight.Medium,
        fontSize = 32.sp,
        color = if (isFocused) {
            Color.Black
        } else {
            Color.Black
        },
        textAlign = TextAlign.Center,
    )
}