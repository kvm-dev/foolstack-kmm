package ru.foolstack.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.foolstack.model.ProgressState

open class BaseViewModel : ViewModel() {
    private val _progressState = MutableStateFlow(ProgressState.LOADING)
    val progressState: StateFlow<ProgressState> = _progressState.asStateFlow()

     fun updateState(state: ProgressState){
        _progressState.value = state
    }

    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        //something error
    }
}