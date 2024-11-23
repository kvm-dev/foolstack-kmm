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
import ru.foolstack.viewmodel.BaseViewModel

class InterviewsViewModel(private val interactor: InterviewsInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<InterviewsViewState>(
        InterviewsViewState.LoadingState(lang = interactor.getCurrentLang())
    )

    val uiState: StateFlow<InterviewsViewState> = _uiState.asStateFlow()

    fun initViewModel() = with(viewModelScope + coroutineExceptionHandler) {
        if(progressState.value == ProgressState.LOADING){
            launch {
                val professionId = interactor.getProfessionId()
                interactor.materialsState.collect{ resultState->
                    interactor.profileState.collect{ profileState->
                        interactor.professionsState.collect{ professionsState->
                            _uiState.update { interactor.checkState(state = resultState, professionId = professionId, professionsState = professionsState, profileState = profileState) }
                            updateState(ProgressState.COMPLETED)
                        }
                    }
                }
            }
        }
    }

    fun refresh() = with(viewModelScope + coroutineExceptionHandler){
        val lang = interactor.getCurrentLang()
        _uiState.update { InterviewsViewState.LoadingState(lang = lang) }
        launch {
            interactor.getMaterialsFromServer()
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
}