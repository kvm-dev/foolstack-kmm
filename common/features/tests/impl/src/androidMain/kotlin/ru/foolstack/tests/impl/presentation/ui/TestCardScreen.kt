package ru.foolstack.tests.impl.presentation.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.model.ProgressState
import ru.foolstack.tests.impl.mapper.Mapper
import ru.foolstack.tests.impl.presentation.viewmodel.TestCardViewModel
import ru.foolstack.ui.R
import ru.foolstack.ui.components.GreenDialog
import ru.foolstack.ui.components.TestAnswerRadioButtons
import ru.foolstack.ui.components.TestCardQuestion
import ru.foolstack.ui.components.TestCardTimer
import ru.foolstack.ui.components.TestResult
import ru.foolstack.ui.components.Title
import ru.foolstack.ui.components.TopBar
import ru.foolstack.ui.components.YellowButton
import ru.foolstack.ui.model.Lang
import kotlin.math.ceil

@SuppressLint("RestrictedApi")
@Composable
fun TestCardScreen(testCardViewModel: TestCardViewModel = koinViewModel(), testId: Int, navigateToMain: ()->Unit) {
    val isVisibleBackDialog = remember { mutableStateOf(false) }
    val isAnswersVisible = remember { mutableStateOf(true) }

    val backDispatcher =
        checkNotNull(LocalOnBackPressedDispatcherOwner.current).onBackPressedDispatcher
    BackHandler {
            isVisibleBackDialog.value = true
        }
    val viewModelState by testCardViewModel.progressState.collectAsState()
    when (viewModelState) {
        ProgressState.COMPLETED -> {
            Log.d("realy testCard Complete", "yes")
            val testState by testCardViewModel.uiState.collectAsState()
            when (testState) {

                is TestCardViewState.Idle -> {
                    Log.d("test in state is", "Error")
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(top = 40.dp)
                    ) {
                        TopBar(
                            screenTitle = if ((testState as TestCardViewState.Idle).lang is LangResultDomain.Ru) {
                                "Тест"
                            } else {
                                "Test"
                            }, action = { backDispatcher.onBackPressed() })
                        Column(modifier = Modifier.align(Alignment.Center)) {
                            val bugBitmap = ImageBitmap.imageResource(id = R.drawable.bug_icon)
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(100.dp, 10.dp),
                                bitmap = bugBitmap,
                                contentDescription = "FoolStack"
                            )
                            Title(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = if ((testState as TestCardViewState.Idle).lang is LangResultDomain.Ru) {
                                    "Что-то пошло не так..."
                                } else {
                                    "Something went wrong..."
                                }
                            )
                            YellowButton(
                                onClick = { backDispatcher.onBackPressed() },
                                text = if ((testState as TestCardViewState.Idle).lang is LangResultDomain.Ru) {
                                    "Вернуться к тестам"
                                } else {
                                    "Return to tests"
                                },
                                isEnabled = true,
                                isLoading = false,
                                modifier = Modifier
                                    .padding(top = 30.dp, start = 16.dp, end = 16.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }

                is TestCardViewState.SuccessState -> {
                    val questionNumber = remember { mutableIntStateOf(0) }
                    val rightAnswers = remember { mutableIntStateOf(0) }
                    val selectedValue = remember { mutableStateOf("") }
                    Log.d("testCard in state is", "Success")
                    val successState = (testState as TestCardViewState.SuccessState)
                    val questionsSize = successState.test?.questions?.size ?:0
                        Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.align(Alignment.TopCenter)) {
                            Box(modifier = Modifier
                                .padding(top = 60.dp)
                                .fillMaxWidth()
                                .wrapContentHeight()) {
                                    TopBar(
                                        screenTitle = successState.test?.testName ?: "",
                                        action = { backDispatcher.onBackPressed() },
                                        isDark = false,
                                        isTitleVisible = true,
                                        isIconVisible = false
                                    )
                            }
                            if(isAnswersVisible.value){
                                TestCardQuestion(text = successState.test?.questions?.get(questionNumber.intValue)?.questionText?: "", modifier = Modifier )
                            }
                            else{
                                TestCardQuestion(text = if(successState.lang is LangResultDomain.Ru) {"Тестирование завершено"} else {"Test is finished"}, modifier = Modifier )

                            }
                            Spacer(modifier = Modifier
                                .height(22.dp))
                            if(isAnswersVisible.value){
                                TestCardTimer(
                                    lang = if(successState.lang is LangResultDomain.Ru){Lang.RU} else {Lang.ENG},
                                    timerValue = successState.test?.testTimeLimit?: 0,
                                    modifier = Modifier,
                                    onStop = {
                                        testCardViewModel.sendResult(testId = testId, testResult = getResult(successState.test?.questions?.size?:1, rightAnswers = rightAnswers.intValue))
                                        isAnswersVisible.value = false
                                    }
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                if(isAnswersVisible.value){
                                    TestAnswerRadioButtons(answers = Mapper().mapVariantsToStringList(
                                        successState.test?.questions?.get(questionNumber.intValue)?.variants), selectedValue = selectedValue )
                                    Spacer(modifier = Modifier.
                                    weight(1F))
                                    if(questionNumber.intValue != questionsSize-1) {
                                        YellowButton(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 20.dp, vertical = 16.dp),
                                            text = if(successState.lang is LangResultDomain.Ru){"Ответить"} else {"Next"},
                                            onClick = {
                                                if(questionNumber.intValue<=questionsSize-1){
                                                    if((successState.test?.questions?.get(questionNumber.intValue)?.variants?.find { it.isRight }?.variantText
                                                            ?: 0) == selectedValue.value
                                                    ){
                                                        rightAnswers.intValue++
                                                    }
                                                    if(questionNumber.intValue<questionsSize-1){
                                                        questionNumber.intValue++
                                                    }
                                                }
                                                selectedValue.value = ""
                                            },
                                            isEnabled = selectedValue.value.isNotEmpty() && isAnswersVisible.value,
                                            isLoading = false
                                        )
                                    }
                                    else{
                                        YellowButton(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 20.dp, vertical = 16.dp),
                                            text = if(successState.lang is LangResultDomain.Ru){"Завершить"} else {"Finish"},
                                            onClick = {
                                                if(questionNumber.intValue<=questionsSize-1){
                                                    if((successState.test?.questions?.get(questionNumber.intValue)?.variants?.find { it.isRight }?.variantText
                                                            ?: 0) == selectedValue.value
                                                    ){
                                                        rightAnswers.intValue++
                                                    }
                                                    if(questionNumber.intValue<questionsSize-1){
                                                        questionNumber.intValue++
                                                    }
                                                    else{
                                                        isAnswersVisible.value = false
                                                    }
                                                }
                                                selectedValue.value = ""
                                                testCardViewModel.sendResult(testId = testId, testResult = getResult(sumQuestion = successState.test?.questions?.size?:1, rightAnswers = rightAnswers.intValue))
                                            },
                                            isEnabled = selectedValue.value.isNotEmpty() && isAnswersVisible.value,
                                            isLoading = false
                                        )
                                    }

                                }
                                else{
                                    val startText = if(successState.lang is LangResultDomain.Ru){"Вам удалось ответить "} else{"Your answers is "}
                                    val resultText = if(successState.lang is LangResultDomain.Ru){"верно в ${getResult(successState.test?.questions?.size?:1, rightAnswers = rightAnswers.intValue)}% случаев."} else{"right in ${getResult(successState.test?.questions?.size?:1, rightAnswers = rightAnswers.intValue)}% of cases. "}
                                    val endText = if(successState.lang is LangResultDomain.Ru){" Через некоторое время вы сможете пройти данный тест вновь.\n\nОбращаем внимание на то, что в случае повторного прохождения теста мы сохраним только последний результат."} else{"After some time, if necessary, you can take this test again.\n\nPlease, remember that in case of re-taking the test, we save only the last result."}
                                    val buttonText = if(successState.lang is LangResultDomain.Ru){ "Ок, понятно" } else{"Ok, understood"}
                                    TestResult(startText = startText, resultText = resultText, endText = endText, buttonText = buttonText, onClick = navigateToMain, modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp))
                                }

                            }

                            GreenDialog(
                                title = if(successState.lang is LangResultDomain.Ru){"Обрати внимание"} else {"Attention"},
                                text = if(successState.lang is LangResultDomain.Ru){"Покидая экран тестирования, ты потеряешь возможность пройти его заново в ближайшие 10 дней.\n\nТекущий результат будет зафиксирован"} else {"If you left this screen you will lose opportunity to try it again in the next 10 days.\n\nThe current result will be saved"},
                                generalActionText = if(successState.lang is LangResultDomain.Ru){"Да, завершить тестирование"} else {"Yes, finish test"},
                                secondaryActionText = if(successState.lang is LangResultDomain.Ru){"Продолжить тестирование"} else {"Continue test"},
                                onGeneralActionClick = {
                                    testCardViewModel.sendResult(testId = testId, testResult = getResult(sumQuestion = successState.test?.questions?.size?:1, rightAnswers = rightAnswers.intValue))
                                    isVisibleBackDialog.value = false
                                    navigateToMain()
                                                       },
                                onSecondaryActionClick = {
                                    isVisibleBackDialog.value = false
                                                         },
                                isVisible = isVisibleBackDialog
                            )
                        }
                    }
                }
            }
        }

        else -> {
            Log.d("testCard realy complete", "no")
            testCardViewModel.initViewModel(testId = testId)
        }
    }
}

private fun getResult(sumQuestion: Int, rightAnswers: Int): Int{
    return ceil((rightAnswers.toFloat()/sumQuestion.toFloat()*100)).toInt()
}