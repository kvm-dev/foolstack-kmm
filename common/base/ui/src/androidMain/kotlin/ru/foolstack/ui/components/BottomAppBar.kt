package ru.foolstack.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.foolstack.ui.R
import ru.foolstack.ui.theme.DisabledColor
import ru.foolstack.ui.theme.MainGreen
import ru.foolstack.ui.theme.MainOrange

@Composable
fun BottomAppBarCompose(selectedState: MutableState<BottomIcons>) {
    BottomAppBar(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(32.dp, 32.dp, 0.dp, 0.dp)), containerColor = MaterialTheme.colorScheme.MainGreen,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row() {
                    IconButton(
                        onClick = {
                            selectedState.value = BottomIcons.TESTS
                        }) {
                        val icon = painterResource(id = R.drawable.home)
                        Icon(
                            painter = icon,
                            contentDescription = null,
                            tint = if (selectedState.value == BottomIcons.TESTS) MaterialTheme.colorScheme.MainOrange else MaterialTheme.colorScheme.DisabledColor
                        )
                    }
                }
                Row() {
                    IconButton(onClick = {
                        selectedState.value = BottomIcons.TESTS
                    }) {
                        val icon = painterResource(id = R.drawable.tests)
                        Icon(
                            painter = icon,
                            contentDescription = null,
                            tint = if (selectedState.value == BottomIcons.TESTS) MaterialTheme.colorScheme.MainOrange else MaterialTheme.colorScheme.DisabledColor
                        )
                    }
                }
            }
        })
}