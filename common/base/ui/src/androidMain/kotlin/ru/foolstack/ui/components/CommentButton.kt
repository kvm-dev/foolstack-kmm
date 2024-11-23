package ru.foolstack.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.foolstack.ui.R
import ru.foolstack.ui.theme.DisabledButtonContent
import ru.foolstack.ui.theme.DisabledColor
import ru.foolstack.ui.theme.ServiceBackground

@Composable
fun CommentButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.size(24.dp),
        contentPadding = PaddingValues(4.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            disabledContainerColor = MaterialTheme.colorScheme.DisabledColor,
            disabledContentColor = MaterialTheme.colorScheme.DisabledButtonContent,
            containerColor = MaterialTheme.colorScheme.ServiceBackground
        )
    ) {
        // Inner content including an icon and a text label
        Icon(
            painter = painterResource(R.drawable.comment_icon),
            contentDescription = "Comment",
            modifier = Modifier.size(20.dp)
        )
    }
}