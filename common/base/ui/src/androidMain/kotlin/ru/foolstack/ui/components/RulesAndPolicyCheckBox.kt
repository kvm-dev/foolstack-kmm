package ru.foolstack.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.foolstack.ui.model.Lang
import ru.foolstack.ui.theme.MainBlack
import ru.foolstack.ui.theme.MainGreen
import ru.foolstack.ui.theme.MainWhite
import ru.foolstack.ui.theme.MainYellow
import ru.foolstack.ui.theme.montserratFamily

@Composable
fun RulesAndPolicyCheckBox(
    lang: Lang,
    checked: MutableState<Boolean>
) {
    val shape = RoundedCornerShape(10.dp)
    val agree = if (lang == Lang.RU) {
        "Я соглашаюсь с "
    } else {
        "I agree with "
    }
    val and = if (lang == Lang.RU) {
        " и "
    } else {
        " and "
    }
    val rules = if (lang == Lang.RU) {
        "правилами"
    } else {
        "rules"
    }
    val policy = if (lang == Lang.RU) {
        "политиками"
    } else {
        "policies"
    }

    Row(modifier = Modifier.
    fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp, bottom = 20.dp)) {
        Checkbox(
            checked = checked.value,
            onCheckedChange = { isChecked -> checked.value = isChecked },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.MainGreen,
                uncheckedColor = MaterialTheme.colorScheme.MainYellow,
                checkmarkColor = MaterialTheme.colorScheme.MainWhite
            )
        )
        Text(modifier = Modifier
            .align(Alignment.CenterVertically),
            fontFamily = montserratFamily, fontWeight = FontWeight.Normal, style = TextStyle(
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.MainBlack,
                textAlign = TextAlign.Start
            ),
            text = buildAnnotatedString {
                append(agree)
                withLink(
                    LinkAnnotation.Url(
                        url = "https://foolstack.ru/rules/",
                        TextLinkStyles(style = SpanStyle(color = MaterialTheme.colorScheme.MainGreen))
                    )
                ) {
                    append(rules)
                }
                append(and)
                withLink(
                    LinkAnnotation.Url(
                        url = "https://foolstack.ru/policy/",
                        TextLinkStyles(style = SpanStyle(color = MaterialTheme.colorScheme.MainGreen))
                    )
                ) {
                    append(policy)
                }
            })

    }
}
