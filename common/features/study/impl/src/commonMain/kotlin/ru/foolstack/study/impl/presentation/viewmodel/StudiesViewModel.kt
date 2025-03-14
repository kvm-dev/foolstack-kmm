package ru.foolstack.study.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.foolstack.model.ProgressState
import ru.foolstack.study.impl.domain.interactor.StudiesInteractor
import ru.foolstack.study.impl.presentation.ui.StudiesViewState
import ru.foolstack.utils.model.ResultState
import ru.foolstack.viewmodel.BaseViewModel

class StudiesViewModel(private val interactor: StudiesInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<StudiesViewState>(
        StudiesViewState.LoadingState(lang = interactor.getCurrentLang())
    )

    val uiState: StateFlow<StudiesViewState> = _uiState.asStateFlow()

    var asMode = false

    fun initViewModel() = with(viewModelScope + coroutineExceptionHandler) {
        if(progressState.value == ProgressState.LOADING){
            launch {
                asMode = interactor.isAsModeActive()
                if(interactor.studiesState.value !is ResultState.Success || interactor.studiesState.value !is ResultState.Loading){
                    if(interactor.isConnectionAvailable()){
                        interactor.getStudiesFromServer()
                    }
                    else{
                        interactor.getStudiesFromLocal()
                    }
                }
            }
            launch {
                interactor.studiesState.collect{ resultState->
                    _uiState.update { interactor.checkState(resultState) }
                    updateState(ProgressState.COMPLETED)
                }
            }
        }
    }

    fun refresh() = with(viewModelScope + coroutineExceptionHandler){
        val lang = interactor.getCurrentLang()
        _uiState.update { StudiesViewState.LoadingState(lang = lang) }
        launch {
            if(interactor.isConnectionAvailable()){
                interactor.getStudiesFromServer()
            }
            else{
                interactor.getStudiesFromLocal()
            }
            updateState(ProgressState.LOADING)
            initViewModel()
        }
    }

    fun updateFilters(subName: String){
        if(subName.isNotEmpty()){
            val current = uiState.value as StudiesViewState.SuccessState
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

    fun onStudyClick(url: String){
        interactor.openInBrowser(url)
    }

    fun isConnectionAvailable() = interactor.isConnectionAvailable()

    fun getCurrentLang() = interactor.getCurrentLang()
}