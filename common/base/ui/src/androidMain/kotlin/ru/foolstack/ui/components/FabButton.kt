package ru.foolstack.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.foolstack.ui.R
import ru.foolstack.ui.theme.DisabledColor
import ru.foolstack.ui.theme.MainGreen
import ru.foolstack.ui.theme.MainOrange

@Composable
fun FabButton(selectedState: MutableState<BottomIcons>) {
    FloatingActionButton(
        modifier = Modifier
            .size(64.dp)
            .offset(y = 50.dp)
            .border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.MainGreen,
                shape = RoundedCornerShape(50.dp)
            ),
        interactionSource = remember { MutableInteractionSource() },
        shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 100)),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = Color.Black,
        //elevation = FloatingActionButtonDefaults.elevation(),
        onClick = {
            selectedState.value = BottomIcons.TESTS
        },
    ) {
        val icon = painterResource(id = R.drawable.interview)
        Icon(painter = icon, contentDescription = null,
            tint = if (selectedState.value == BottomIcons.INTERVIEW) MaterialTheme.colorScheme.MainOrange else MaterialTheme.colorScheme.DisabledColor)
    }
}