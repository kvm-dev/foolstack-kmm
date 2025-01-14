package ru.foolstack.tests.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.foolstack.model.ProgressState
import ru.foolstack.tests.impl.domain.interactor.TestsInteractor
import ru.foolstack.tests.impl.presentation.ui.TestsViewState
import ru.foolstack.viewmodel.BaseViewModel

class TestsViewModel(private val interactor: TestsInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<TestsViewState>(
        TestsViewState.LoadingState(lang = interactor.getCurrentLang())
    )

    val uiState: StateFlow<TestsViewState> = _uiState.asStateFlow()

    fun initViewModel() = with(viewModelScope + coroutineExceptionHandler) {
        if(progressState.value == ProgressState.LOADING){
            launch {
                val professionId = interactor.getProfessionId()
                interactor.testsState.collect{ resultState->
                    interactor.passedTestsState.collect { passedTestsState ->
                        interactor.profileState.collect { profileState ->
                            interactor.professionsState.collect { professionsState ->
                                _uiState.update {
                                    interactor.checkState(
                                        testsState = resultState,
                                        professionId = professionId,
                                        professionsState = professionsState,
                                        profileState = profileState,
                                        passedTestsState = passedTestsState
                                    )
                                }
                                updateState(ProgressState.COMPLETED)
                            }
                        }
                    }
                }
            }
        }
    }

    fun refresh() = with(viewModelScope + coroutineExceptionHandler){
        val lang = interactor.getCurrentLang()
        _uiState.update { TestsViewState.LoadingState(lang = lang) }
        launch {
            interactor.getTestsFromServer()
            interactor.getPassedTestsFromServer()
            updateState(ProgressState.LOADING)
            initViewModel()
        }
    }

    fun sendComment(testId: Int, testResult: Int) = with(viewModelScope + coroutineExceptionHandler){
        launch { interactor.sendResult(testId = testId, testResult = testResult) }
    }

    fun navigateToTest(navController: NavController, testId: Int, testDestination: String){
        val route = "$testDestination/{testId}"
        navController.navigate(
            route.replace(
                oldValue = "{testId}",
                newValue = testId.toString()
            )
        )
    }
}