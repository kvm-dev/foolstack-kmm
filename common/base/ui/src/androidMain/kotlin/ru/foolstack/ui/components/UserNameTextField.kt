package ru.foolstack.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.theme.ErrorColor
import ru.foolstack.ui.theme.FieldsDefaultBorderColor
import ru.foolstack.ui.theme.FieldsPlaceholderColor
import ru.foolstack.ui.theme.MainBlack
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun UserNameTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    onChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String,
    isEnabled: Boolean,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            enabled = isEnabled,
            value = value,
            onValueChange = {
                if (!it.contains("\n"))
                    onChange(it)
            },
            placeholder = {
                Text(text = placeholder, fontFamily = montserratFamily, fontWeight = FontWeight.Medium,
                    fontSize = 16.sp, color = MaterialTheme.colorScheme.FieldsPlaceholderColor)
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = MaterialTheme.colorScheme.MainBlack,
                unfocusedBorderColor = MaterialTheme.colorScheme.FieldsDefaultBorderColor,
                focusedTextColor = MaterialTheme.colorScheme.MainBlack,
                focusedBorderColor = MaterialTheme.colorScheme.FieldsDefaultBorderColor,
                errorBorderColor = MaterialTheme.colorScheme.ErrorColor,
                cursorColor = MaterialTheme.colorScheme.MainBlack
            ),
            shape = RoundedCornerShape(8.dp),
            isError = isError
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
}