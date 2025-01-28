package ru.foolstack.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.R
import ru.foolstack.ui.theme.FieldsPlaceholderColor
import ru.foolstack.ui.theme.MainGreen
import ru.foolstack.ui.theme.PrimaryTitleColor
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun TestAnswerRadioButtons(answers:List<String>, selectedValue: MutableState<String>) {
        Column(Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)) {
            answers.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .selectable(
                            selected = (selectedValue.value == item),
                            onClick = { selectedValue.value = item },
                            role = Role.RadioButton
                        )
                        .padding(8.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .weight(0.9F)
                            .padding(end = 12.dp),
                        text = item,
                        color = MaterialTheme.colorScheme.PrimaryTitleColor,
                        fontFamily = montserratFamily, fontWeight = FontWeight.Normal, style = TextStyle(
                            fontSize = 14.sp,
                            fontStyle = FontStyle.Normal)
                    )
                    IconToggleButton(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(24.dp)
                            .weight(0.1F),
                        checked = selectedValue.value == item,
                        onCheckedChange = {
                            selectedValue.value = item
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                if (selectedValue.value == item) {
                                    R.drawable.radio_selected_icon
                                } else {
                                    R.drawable.radio_unselected_icon
                                }
                            ),
                            contentDescription = null,
                            tint = if (selectedValue.value == item) {
                                MaterialTheme.colorScheme.MainGreen
                            } else {
                                MaterialTheme.colorScheme.FieldsPlaceholderColor
                            }
                        )
                    }
                }
            }
        }
}
