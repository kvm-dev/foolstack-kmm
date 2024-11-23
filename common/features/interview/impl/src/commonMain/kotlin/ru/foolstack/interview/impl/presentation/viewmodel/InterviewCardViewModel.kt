package ru.foolstack.interview.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.foolstack.interview.impl.domain.interactor.InterviewCardInteractor
import ru.foolstack.interview.impl.presentation.ui.InterviewCardViewState
import ru.foolstack.model.ProgressState
import ru.foolstack.utils.model.ResultState
import ru.foolstack.viewmodel.BaseViewModel

class InterviewCardViewModel(private val interactor: InterviewCardInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<InterviewCardViewState>(
        InterviewCardViewState.Idle(lang = interactor.getCurrentLang()))

    val uiState: StateFlow<InterviewCardViewState> = _uiState.asStateFlow()

    fun initViewModel(materialId: Int) = with(viewModelScope + coroutineExceptionHandler) {
        if(progressState.value == ProgressState.LOADING){
            launch {
                interactor.materialState.collect{ resultState->
                    if(resultState is ResultState.Success){
                        _uiState.update { InterviewCardViewState.SuccessState(
                            isHaveConnection = interactor.isConnectionAvailable(),
                            lang = interactor.getCurrentLang(),
                            material  = resultState.data?.materials?.find { it.materialId == materialId }) }
                        updateState(ProgressState.COMPLETED)
                    }
                }
            }
        }
    }

    fun sendComment(materialId: Int, comment: String) = with(viewModelScope + coroutineExceptionHandler){
        launch { interactor.sendComment(materialId = materialId, comment = comment) }
    }
}