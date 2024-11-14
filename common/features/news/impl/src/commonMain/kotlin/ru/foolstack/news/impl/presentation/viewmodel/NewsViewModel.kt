package ru.foolstack.news.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.foolstack.model.ProgressState
import ru.foolstack.news.impl.domain.interactor.NewsInteractor
import ru.foolstack.news.impl.presentation.ui.NewsViewState
import ru.foolstack.viewmodel.BaseViewModel

class NewsViewModel(private val interactor: NewsInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<NewsViewState>(
        NewsViewState.LoadingState(lang = interactor.getCurrentLang())
    )

    val uiState: StateFlow<NewsViewState> = _uiState.asStateFlow()

    fun initViewModel() = with(viewModelScope) {
        if(progressState.value == ProgressState.LOADING){
            viewModelScope.launch {
                interactor.newsState.collect{ resultState->
                    _uiState.update { interactor.checkState(resultState) }
                    updateState(ProgressState.COMPLETED)
                }
            }
        }
    }

    fun refresh() = with(viewModelScope){
        val lang = interactor.getCurrentLang()
        _uiState.update { NewsViewState.LoadingState(lang = lang) }
        viewModelScope.launch {
            interactor.getNewsFromServer()
            updateState(ProgressState.LOADING)
            initViewModel()
        }
    }

    fun navigateToSingleNews(navController: NavController, newsId: Int, newsDestination: String){
        val route = "$newsDestination/{newsId}"
        navController.navigate(
            route.replace(
                oldValue = "{newsId}",
                newValue = newsId.toString()
            )
        )
    }
}