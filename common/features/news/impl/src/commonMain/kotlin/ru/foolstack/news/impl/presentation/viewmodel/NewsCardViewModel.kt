package ru.foolstack.news.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.foolstack.model.ProgressState
import ru.foolstack.news.impl.domain.interactor.NewsCardInteractor
import ru.foolstack.news.impl.presentation.ui.NewsCardViewState
import ru.foolstack.utils.model.ResultState
import ru.foolstack.viewmodel.BaseViewModel

class NewsCardViewModel(private val interactor: NewsCardInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<NewsCardViewState>(
        NewsCardViewState.Idle(lang = interactor.getCurrentLang()))

    val uiState: StateFlow<NewsCardViewState> = _uiState.asStateFlow()

    fun initViewModel(newsId: Int) {
        if(progressState.value == ProgressState.LOADING){
            viewModelScope.launch {
                interactor.newsState.collect{ resultState->
                    if(resultState is ResultState.Success){
                        _uiState.update { NewsCardViewState.SuccessState(
                            isHaveConnection = interactor.isConnectionAvailable(),
                            lang = interactor.getCurrentLang(),
                            singleNewsDomain = resultState.data?.news?.find { it.newsId == newsId }) }
                        updateState(ProgressState.COMPLETED)
                    }
                }
            }
        }
    }

    fun shareLink(url: String){
        interactor.shareLink(url)
    }
}