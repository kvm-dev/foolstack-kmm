package ru.foolstack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.theme.CardText
import ru.foolstack.ui.theme.DisabledColor
import ru.foolstack.ui.theme.montserratFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentBottomSheet(
    isShowBottomSheet: MutableState<Boolean>,
    sendComment: () -> Unit,
    onValueChange: (String) -> Unit,
    text: MutableState<String>,
    lang: Lang
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isShowBottomSheet.value) {
            ModalBottomSheet(
                onDismissRequest = {
                    isShowBottomSheet.value = false
                },
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                containerColor = MaterialTheme.colorScheme.background,
                tonalElevation = 16.dp,
                dragHandle = {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .width(50.dp)
                            .height(6.dp)
                            .clip(RoundedCornerShape(50))
                            .background(MaterialTheme.colorScheme.DisabledColor)
                            .align(Alignment.Center)
                    )
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopBar(
                        screenTitle = if (lang == Lang.RU) {
                            "Мнения и предложения"
                        } else {
                            "Opinions and suggestions"
                        }, action = { if(isShowBottomSheet.value){
                            isShowBottomSheet.value = false }
                        }
                    )
                    val descriptionText = if(lang == Lang.RU){ "Твое мнение очень важно для нас:)\nВсе твои предложения и замечания будут обязательно рассматриваться в индивидуальном порядке.\n\nСпасибо, что помогаешь делать наш сервис лучше!" } else { "Your opinion is very important for us:)\nAll your suggestions and comments will be considered individually.\n\nThank you for help!" }
                    Text(
                        modifier = Modifier
                            .padding(top = 34.dp, start = 16.dp, end = 16.dp),
                        text = descriptionText, fontFamily = montserratFamily, fontWeight = FontWeight.Normal, style = TextStyle(
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.CardText,
                            textAlign = TextAlign.Start
                        ),
                        fontStyle = FontStyle.Italic
                    )

                    TextInput(modifier = Modifier
                        .padding(top = 20.dp),
                        placeholder = if(lang == Lang.RU){ "Твой текст" } else { "Your text" },
                        text = text,
                        onValueChange = onValueChange)

                    YellowButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 32.dp),
                        text = if(lang == Lang.RU){ "Отправить" } else { "Send"},
                        onClick = sendComment,
                        isEnabled = text.value.isNotEmpty(),
                        isLoading = false
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}