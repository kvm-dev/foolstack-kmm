package ru.foolstack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.foolstack.ui.R
import ru.foolstack.ui.theme.Divider
import ru.foolstack.ui.theme.MainBackground
import ru.foolstack.ui.theme.NavigationDisabled
import ru.foolstack.ui.theme.MainDarkGreen

@Composable
fun BottomAppBar(selectedState: MutableState<BottomIcons>, isShow: MutableState<Boolean>, lang: String, onClickMain: () -> Unit, onClickNews: () -> Unit, onClickInterviews: () -> Unit) {
    if(isShow.value){
        Column(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.MainBackground)
        ) {
            HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), thickness = 1.dp, color = MaterialTheme.colorScheme.Divider)
            BottomAppBar(modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red),
                containerColor = MaterialTheme.colorScheme.MainBackground,
//            .clip(RoundedCornerShape(0.dp, 32.dp, 0.dp, 0.dp)), containerColor = MaterialTheme.colorScheme.MainGreen,
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(modifier = Modifier
                            .padding(10.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ){
                              selectedState.value = BottomIcons.MAIN
                              onClickMain()
                            }
                        ) {
                                val icon = painterResource(id = R.drawable.home_icon)
                                Column(horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Bottom) {
                                    Icon(
                                        painter = icon,
                                        contentDescription = null,
                                        tint = if (selectedState.value == BottomIcons.MAIN) MaterialTheme.colorScheme.MainDarkGreen else MaterialTheme.colorScheme.NavigationDisabled
                                    )
                                    Text(color = if (selectedState.value == BottomIcons.MAIN) MaterialTheme.colorScheme.MainDarkGreen else MaterialTheme.colorScheme.NavigationDisabled, text = if(lang=="RU"){BottomIcons.MAIN.rusTitle} else {BottomIcons.MAIN.engTitle})
                                }
                        }
                        Row(modifier = Modifier
                            .padding(10.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ){
                                selectedState.value = BottomIcons.INTERVIEW
                                onClickInterviews()
                            }
                        ) {
                                val icon = painterResource(id = R.drawable.interview_icon)
                                Column(horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom) {
                                Icon(
                                    painter = icon,
                                    contentDescription = null,
                                    tint = if (selectedState.value == BottomIcons.INTERVIEW) MaterialTheme.colorScheme.MainDarkGreen else MaterialTheme.colorScheme.NavigationDisabled
                                )
                                Text(color = if (selectedState.value == BottomIcons.INTERVIEW) MaterialTheme.colorScheme.MainDarkGreen else MaterialTheme.colorScheme.NavigationDisabled, text = if(lang=="RU"){BottomIcons.INTERVIEW.rusTitle} else {BottomIcons.INTERVIEW.engTitle})
                            }
                        }
                        Row(modifier = Modifier
                            .padding(10.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ){
                                selectedState.value = BottomIcons.TESTS
                            }
                        ) {
                                val icon = painterResource(id = R.drawable.tests_icon)
                                Column(horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom) {
                                Icon(
                                    painter = icon,
                                    contentDescription = null,
                                    tint = if (selectedState.value == BottomIcons.TESTS) MaterialTheme.colorScheme.MainDarkGreen else MaterialTheme.colorScheme.NavigationDisabled
                                )
                                Text(color = if (selectedState.value == BottomIcons.TESTS) MaterialTheme.colorScheme.MainDarkGreen else MaterialTheme.colorScheme.NavigationDisabled, text = if(lang=="RU"){BottomIcons.TESTS.rusTitle} else {BottomIcons.TESTS.engTitle})
                            }
                        }
                        Row(modifier = Modifier
                            .padding(10.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ){
                                selectedState.value = BottomIcons.NEWS
                                onClickNews()
                            }
                        ) {
                                val icon = painterResource(id = R.drawable.news_icon)
                                Column(horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom) {
                                Icon(
                                    painter = icon,
                                    contentDescription = null,
                                    tint = if (selectedState.value == BottomIcons.NEWS) MaterialTheme.colorScheme.MainDarkGreen else MaterialTheme.colorScheme.NavigationDisabled
                                )
                                Text(color = if (selectedState.value == BottomIcons.NEWS) MaterialTheme.colorScheme.MainDarkGreen else MaterialTheme.colorScheme.NavigationDisabled, text = if(lang=="RU"){BottomIcons.NEWS.rusTitle} else {BottomIcons.NEWS.engTitle})
                            }
                        }
                    }
                }
            )
        }
    }
}