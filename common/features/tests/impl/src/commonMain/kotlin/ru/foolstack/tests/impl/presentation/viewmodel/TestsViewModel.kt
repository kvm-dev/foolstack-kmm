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
import ru.foolstack.utils.model.ResultState
import ru.foolstack.viewmodel.BaseViewModel

class TestsViewModel(private val interactor: TestsInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<TestsViewState>(
        TestsViewState.LoadingState(lang = interactor.getCurrentLang())
    )

    val uiState: StateFlow<TestsViewState> = _uiState.asStateFlow()

    fun initViewModel() = with(viewModelScope + coroutineExceptionHandler) {
        if(progressState.value == ProgressState.LOADING){
            launch {
                if(interactor.testsState.value !is ResultState.Success){
                    if(interactor.isConnectionAvailable()){
                        interactor.getTestsFromServer()
                        interactor.getPassedTestsFromServer()
                    }
                    else{
                        interactor.getTestsFromLocal()
                        interactor.getPassedTestsFromLocal()
                    }
                }
                val professionId = interactor.getProfessionId()
                interactor.testsState.collect{ resultState->
                    interactor.passedTestsState.collect { passedTestsState ->
                        interactor.profileState.collect { profileState ->
                            interactor.professionsState.collect { professionsState ->
                                if(resultState == ResultState.Idle){
                                    if(interactor.isConnectionAvailable()){
                                        interactor.getTestsFromServer()
                                        interactor.getPassedTestsFromServer()
                                    }
                                    else{
                                        interactor.getTestsFromLocal()
                                        interactor.getPassedTestsFromLocal()}
                                }
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
            if(interactor.isConnectionAvailable()){
                interactor.getTestsFromServer()
                interactor.getPassedTestsFromServer()
            }
            else{
                interactor.getTestsFromLocal()
                interactor.getPassedTestsFromLocal()
            }
            updateState(ProgressState.LOADING)
            initViewModel()
        }
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

    fun isConnectionAvailable() = interactor.isConnectionAvailable()

    fun getCurrentLang() = interactor.getCurrentLang()
}