package ru.foolstack.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SnackbarCompose(isVisible: Boolean, message: String) {
    Scaffold { contentPadding ->

        if (isVisible) {
            Snackbar(modifier = Modifier.padding(contentPadding), content = { Text(message) })
        }
    }
}