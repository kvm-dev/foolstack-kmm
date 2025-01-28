package ru.foolstack.tests.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.foolstack.model.ProgressState
import ru.foolstack.tests.impl.domain.interactor.TestCardInteractor
import ru.foolstack.tests.impl.presentation.ui.TestCardViewState
import ru.foolstack.utils.model.ResultState
import ru.foolstack.viewmodel.BaseViewModel

class TestCardViewModel(private val interactor: TestCardInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<TestCardViewState>(
        TestCardViewState.Idle(lang = interactor.getCurrentLang()))

    val uiState: StateFlow<TestCardViewState> = _uiState.asStateFlow()

    fun initViewModel(testId: Int) = with(viewModelScope + coroutineExceptionHandler) {
        if(progressState.value == ProgressState.LOADING){
            launch {
                interactor.testState.collect{ resultState->
                    if(resultState is ResultState.Success){
                        _uiState.update { TestCardViewState.SuccessState(
                            isHaveConnection = interactor.isConnectionAvailable(),
                            lang = interactor.getCurrentLang(),
                            test = resultState.data?.tests?.find { it.testId == testId }) }
                        updateState(ProgressState.COMPLETED)
                    }
                }
            }
        }
    }

    fun sendResult(testId: Int, testResult: Int) = with(viewModelScope + coroutineExceptionHandler) {
        launch {
            val current = uiState.value as TestCardViewState.SuccessState
            val result = interactor.saveTestResult(testId = testId, testResult = testResult)
            _uiState.update { current.copy(errorMsg = result) }
        }
    }

}