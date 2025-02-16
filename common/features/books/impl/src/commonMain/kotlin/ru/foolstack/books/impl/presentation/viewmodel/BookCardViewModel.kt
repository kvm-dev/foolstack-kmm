package ru.foolstack.books.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.foolstack.books.impl.domain.interactor.BookCardInteractor
import ru.foolstack.books.impl.presentation.ui.BookCardViewState
import ru.foolstack.model.ProgressState
import ru.foolstack.utils.model.ResultState
import ru.foolstack.viewmodel.BaseViewModel

class BookCardViewModel(private val interactor: BookCardInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<BookCardViewState>(
        BookCardViewState.Idle(lang = interactor.getCurrentLang()))

    val uiState: StateFlow<BookCardViewState> = _uiState.asStateFlow()

    var asMode = false
    fun initViewModel(bookId: Int) = with(viewModelScope + coroutineExceptionHandler) {
        if(progressState.value == ProgressState.LOADING){
            launch {
                asMode = interactor.isAsModeActive()
                interactor.booksState.collect{ resultState->
                    if(resultState is ResultState.Success){
                        _uiState.update { BookCardViewState.SuccessState(
                            isHaveConnection = interactor.isConnectionAvailable(),
                            lang = interactor.getCurrentLang(),
                            book = resultState.data?.books?.find { it.bookId == bookId }) }
                        updateState(ProgressState.COMPLETED)
                    }
                }
            }
        }
    }

    fun onClickLink(url: String){
        interactor.openInBrowser(url)
    }
}