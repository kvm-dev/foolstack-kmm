package ru.foolstack.authorized.impl.presentation.ui

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel
import ru.foolstack.authorized.impl.presentation.viewmodel.AuthorizedViewModel
import ru.foolstack.ui.components.Title

@Composable
fun AuthorizedScreen(authorizedViewModel: AuthorizedViewModel = koinViewModel()) {
    Title("AuthorizedScreen")
}
