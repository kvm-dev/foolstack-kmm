package ru.foolstack.tests.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.foolstack.model.ProgressState
import ru.foolstack.profile.api.model.ProfileDomain
import ru.foolstack.tests.impl.domain.interactor.TestsInteractor
import ru.foolstack.tests.impl.presentation.ui.TestsViewState
import ru.foolstack.utils.model.ResultState
import ru.foolstack.viewmodel.BaseViewModel

class TestsViewModel(private val interactor: TestsInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<TestsViewState>(
        TestsViewState.LoadingState(lang = interactor.getCurrentLang())
    )

    val uiState: StateFlow<TestsViewState> = _uiState.asStateFlow()

    fun initViewModel() {
        if(progressState.value == ProgressState.LOADING) {
            viewModelScope.launch {
                if (interactor.testsState.value !is ResultState.Success || interactor.testsState.value !is ResultState.Loading) {
                    if (interactor.isConnectionAvailable()) {
                        interactor.getTestsFromServer()
                        interactor.getPassedTestsFromServer()
                    } else {
                        interactor.getTestsFromLocal()
                        interactor.getPassedTestsFromLocal()
                    }
                }
            }
            viewModelScope.launch(coroutineExceptionHandler + Dispatchers.IO + supervisorJob) {
            val professionId = interactor.getProfessionId()
            interactor.testsState.collect { resultState ->
                if(resultState is ResultState.Success) {
                    interactor.passedTestsState.collect { passedTestsState ->
                        if (passedTestsState is ResultState.Success) {
                            interactor.profileState.collect { profileState ->
                                if (profileState is ResultState.Success) {
                                    interactor.professionsState.collect { professionsState ->
                                        if (resultState == ResultState.Idle) {
                                            if (interactor.isConnectionAvailable()) {
                                                interactor.getTestsFromServer()
                                                interactor.getPassedTestsFromServer()
                                            } else {
                                                interactor.getTestsFromLocal()
                                                interactor.getPassedTestsFromLocal()
                                            }
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
                                } else {
                                    updateState(ProgressState.LOADING)
                                }
                            }
                        } else {
                            updateState(ProgressState.LOADING)
                        }
                    }
                }
                else {
                    updateState(ProgressState.LOADING)
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

    fun isGuest():Boolean{
        return if(interactor.profileState.value is ResultState.Success){
            val userName = (interactor.profileState.value as ResultState.Success<ProfileDomain>).data?.userName
            userName?.isNotEmpty() != true
        } else true
    }
    fun isConnectionAvailable() = interactor.isConnectionAvailable()

    fun getCurrentLang() = interactor.getCurrentLang()

    fun getNotFoundDataTitle() = interactor.getNotFoundDataTitle()

    fun getNotFoundDataDescription() = interactor.getNotFoundDataDescription()
}