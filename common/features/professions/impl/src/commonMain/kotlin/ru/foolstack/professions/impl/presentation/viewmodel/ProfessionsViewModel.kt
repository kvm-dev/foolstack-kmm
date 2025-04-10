package ru.foolstack.professions.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.foolstack.model.ProgressState
import ru.foolstack.professions.impl.domain.interactor.ProfessionsInteractor
import ru.foolstack.professions.impl.presentation.ui.ProfessionsViewState
import ru.foolstack.viewmodel.BaseViewModel

class ProfessionsViewModel(private val interactor: ProfessionsInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<ProfessionsViewState>(
        ProfessionsViewState.LoadingState(lang = interactor.getCurrentLang())
    )

    val uiState: StateFlow<ProfessionsViewState> = _uiState.asStateFlow()

    fun initViewModel() = with(viewModelScope + coroutineExceptionHandler) {
        if(progressState.value == ProgressState.LOADING){
            launch {
                interactor.professionsState.collect{ resultState->
                    _uiState.update { interactor.checkState(resultState) }
                    updateState(ProgressState.COMPLETED)
                }
            }
        }
    }

    fun refresh() = with(viewModelScope + coroutineExceptionHandler){
        val lang = interactor.getCurrentLang()
        _uiState.update { ProfessionsViewState.LoadingState(lang = lang) }
        launch {
            interactor.getProfessionsFromServer()
            updateState(ProgressState.LOADING)
            initViewModel()
        }
    }

    fun saveProfession(professionId: Int) = with(viewModelScope + coroutineExceptionHandler){
        launch {
            interactor.saveProfession(professionId)
            interactor.getMaterials(professionId)
            if(interactor.isConnectionAvailable()){
//                interactor.getMaterialsFromServer(professionId)
                interactor.getTestsFromServer(professionId)
                interactor.getPassedTestsFromServer()
            }
            else{
//                interactor.getMaterialsFromLocal()
                interactor.getTestsFromLocal()
                interactor.getPassedTestsFromLocal()
            }
        }
    }
}