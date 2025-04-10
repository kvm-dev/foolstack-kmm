package ru.foolstack.interview.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.foolstack.interview.impl.domain.interactor.InterviewsInteractor
import ru.foolstack.interview.impl.presentation.ui.InterviewsViewState
import ru.foolstack.model.ProgressState
import ru.foolstack.utils.model.ResultState
import ru.foolstack.viewmodel.BaseViewModel

class InterviewsViewModel(private val interactor: InterviewsInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<InterviewsViewState>(
        InterviewsViewState.LoadingState(lang = interactor.getCurrentLang())
    )

    val uiState: StateFlow<InterviewsViewState> = _uiState.asStateFlow()

    var asMode = false

    fun initViewModel() {
        if(progressState.value == ProgressState.LOADING){
            (viewModelScope + coroutineExceptionHandler + supervisorJob).launch {
                launch(coroutineExceptionHandler) {
                    asMode = interactor.isAsModeActive()
                }
                if (interactor.materialsState.value !is ResultState.Success || interactor.materialsState.value !is ResultState.Loading) {
                    launch(coroutineExceptionHandler) {
                        interactor.getMaterials()
                    }
//                    if (interactor.isConnectionAvailable()) {
//                        launch(coroutineExceptionHandler) {
//                            interactor.getMaterialsFromServer()
//                        }
//                    } else {
//                        launch(coroutineExceptionHandler) {
//                            interactor.getMaterialsFromLocal()
//                        }
//                    }
                }
                else{
                    updateState(ProgressState.LOADING)
                }
            }
            (viewModelScope + coroutineExceptionHandler + supervisorJob).launch{
                val professionId = interactor.getProfessionId()
                interactor.materialsState.collect{ resultState->
                    if(resultState is ResultState.Success){
                        interactor.profileState.collect{ profileState->
                            if(profileState is ResultState.Success){
                                interactor.professionsState.collect{ professionsState->
                                    if(resultState == ResultState.Idle){
                                        launch(coroutineExceptionHandler){
                                            interactor.getMaterials()
                                        }
//                                        if(interactor.isConnectionAvailable()){
//                                            launch(coroutineExceptionHandler) {
//                                                interactor.getMaterialsFromServer()
//                                            }
//                                        }
//                                        else{
//                                            launch(coroutineExceptionHandler) {
//                                                interactor.getMaterialsFromLocal()
//                                            }
//                                        }
                                        updateState(ProgressState.LOADING)
                                    }
                                    if(professionsState is ResultState.Success){
                                        _uiState.update { interactor.checkState(state = resultState, professionId = professionId, professionsState = professionsState, profileState = profileState) }
                                        updateState(ProgressState.COMPLETED)
                                    }
                                    else{
                                        updateState(ProgressState.LOADING)
                                    }
                                }
                            }
                            else{
                                updateState(ProgressState.LOADING)
                            }
                        }
                    }
                    else{
                        if(resultState is ResultState.Idle){
                            interactor.getMaterials()
//                            if(interactor.isConnectionAvailable()){
//                                launch(coroutineExceptionHandler) { interactor.getMaterialsFromServer() }
//                            }
//                            else{
//                                launch(coroutineExceptionHandler) { interactor.getMaterialsFromLocal() }
//                            }
                        }
                        updateState(ProgressState.LOADING)
                    }
                }
            }
        }
    }
    fun refresh() = with(viewModelScope + coroutineExceptionHandler){
        val lang = interactor.getCurrentLang()
        _uiState.update { InterviewsViewState.LoadingState(lang = lang) }
        launch {
            interactor.getMaterials()
//            if(interactor.isConnectionAvailable()){
//                interactor.getMaterialsFromServer()
//            }
//            else{
//                interactor.getMaterialsFromLocal()
//            }
            updateState(ProgressState.LOADING)
            initViewModel()
        }
    }

    fun updateFilters(subName: String){
        if(subName.isNotEmpty()){
            val current = uiState.value as InterviewsViewState.SuccessState
            val selectedFilters = HashSet<String>()
            current.selectedFilters.forEach { sub->
                selectedFilters.add(sub)
            }
            if(selectedFilters.contains(subName)){
                selectedFilters.remove(subName)
            }
            else{
                selectedFilters.add(subName)
            }

            _uiState.update { current.copy(selectedFilters = selectedFilters.toList()) }
        }
    }

    fun sendComment(materialId: Int, comment: String) = with(viewModelScope + coroutineExceptionHandler){
        launch { interactor.sendComment(materialId = materialId, comment = comment) }
    }

    fun goToTelegram(){
        interactor.goToTelegram()
    }

    fun navigateToMaterial(navController: NavController, materialId: Int,interviewDestination: String){
        val route = "$interviewDestination/{materialId}"
        navController.navigate(
            route.replace(
                oldValue = "{materialId}",
                newValue = materialId.toString()
            )
        )
    }

    fun isConnectionAvailable() = interactor.isConnectionAvailable()
    fun getCurrentLang() = interactor.getCurrentLang()

    fun getNotFoundDataTitle() = interactor.getNotFoundDataTitle()

    fun getNotFoundDataDescription() = interactor.getNotFoundDataDescription()
}