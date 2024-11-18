package ru.foolstack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.foolstack.ui.R

@Composable
fun MaterialSection(materialHeader: String, materialText: String, isExpanded: Boolean, onHeaderClicked: () -> Unit) {
    Column {
        Row(modifier = Modifier
            .clickable { onHeaderClicked() }
            .background(Color.LightGray)
            .padding(vertical = 8.dp, horizontal = 16.dp)) {
            Text(
                text = materialHeader,
                modifier = Modifier.weight(1.0f)
            )
            if (isExpanded) {
                Icon(painter = painterResource(id = R.drawable.arrow_up), contentDescription = null)
            } else {
                Icon(painter = painterResource(id = R.drawable.arrow_down), contentDescription = null)
            }
        }
        if(isExpanded){
            Text(text = materialText)
        }
    }
}