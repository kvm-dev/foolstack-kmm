package ru.foolstack.books.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.foolstack.books.impl.domain.interactor.BookCardInteractor
import ru.foolstack.books.impl.presentation.ui.BookCardViewState
import ru.foolstack.model.ProgressState
import ru.foolstack.viewmodel.BaseViewModel

class BookCardViewModel(private val interactor: BookCardInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<BookCardViewState>(
        BookCardViewState.LoadingState(lang = interactor.getCurrentLang())
    )

    val uiState: StateFlow<BookCardViewState> = _uiState.asStateFlow()

    fun initViewModel(bookId: Int) = with(viewModelScope) {
        if(progressState.value == ProgressState.LOADING){
            viewModelScope.launch {
                interactor.booksState.collect{ resultState->
                    _uiState.update { interactor.checkState(state = resultState, bookId = bookId) }
                    updateState(ProgressState.COMPLETED)
                }
            }
        }
    }

    fun onClickLink(url: String){
        interactor.openInBrowser(url)
    }
}