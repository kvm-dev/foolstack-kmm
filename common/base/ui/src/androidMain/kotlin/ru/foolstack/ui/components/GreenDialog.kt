package ru.foolstack.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import ru.foolstack.ui.theme.MainBlack
import ru.foolstack.ui.theme.ServiceText
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun GreenDialog(title: String,
                text: String,
                generalActionText: String = "",
                secondaryActionText: String = "",
                hideGeneralButton: Boolean = false,
                hideSecondaryButton: Boolean = false,
                hideText: Boolean = false,
                isVisible: MutableState<Boolean>,
                onGeneralActionClick:()->Unit,
                isCanClose: Boolean = true,
                onSecondaryActionClick:()->Unit) {
            if (isVisible.value) {
                AlertDialog(
                    properties = DialogProperties(
                        usePlatformDefaultWidth = false
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 14.dp),
                    onDismissRequest = {
                        if(isCanClose){
                            isVisible.value = false
                            onSecondaryActionClick()
                        }
                    },
                    title = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp, top = 32.dp, bottom = 26.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = title, fontFamily = montserratFamily,
                                fontWeight = FontWeight.SemiBold, style = TextStyle(
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.MainBlack,
                                    textAlign = TextAlign.End,
                                )
                            )
                        }
                    },
                    text = {
                        if (!hideText) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp),
                                text = text, fontFamily = montserratFamily,
                                fontWeight = FontWeight.Medium, style = TextStyle(
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.ServiceText,
                                    textAlign = TextAlign.Center,
                                )
                            )
                        }
                    },
                    confirmButton = {
                        if(!hideGeneralButton){
                        GreenButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 20.dp,
                                    vertical = 8.dp
                                ),
                            text = generalActionText,
                            onClick = { onGeneralActionClick() },
                            isEnabled = true,
                            isLoading = false
                        )
                    } },
                    dismissButton = {
                        if (!hideSecondaryButton) {
                        SecondGreenButton(
                            modifier = Modifier
                                .padding(horizontal = 20.dp, vertical = 4.dp),
                        text = secondaryActionText,
                            onClick = { onSecondaryActionClick( )},
                            isEnabled = true
                        )
                    }
                    }
                )
            }

}