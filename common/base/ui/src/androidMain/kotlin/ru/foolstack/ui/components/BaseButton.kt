package ru.foolstack.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BaseButton(onClick: () -> Unit, text: String){
    Button(
        onClick = {
            onClick()
        }
    ) {
        Text(text = text)
    }
}