package ru.foolstack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.foolstack.ui.theme.FoolStackTheme
import ru.foolstack.ui.theme.MainGreen

@Composable
fun BaseBox(){
    FoolStackTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.MainGreen))
    }
}