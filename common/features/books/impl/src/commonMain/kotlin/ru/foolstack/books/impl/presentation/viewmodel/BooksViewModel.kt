package ru.foolstack.books.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.foolstack.books.impl.domain.interactor.BooksInteractor
import ru.foolstack.books.impl.presentation.ui.BooksViewState
import ru.foolstack.model.ProgressState
import ru.foolstack.viewmodel.BaseViewModel

class BooksViewModel(private val interactor: BooksInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<BooksViewState>(
        BooksViewState.LoadingState(lang = interactor.getCurrentLang())
    )

    val uiState: StateFlow<BooksViewState> = _uiState.asStateFlow()

    fun initViewModel() = with(viewModelScope) {
        if(progressState.value == ProgressState.LOADING){
            viewModelScope.launch {
                interactor.booksState.collect{ resultState->
                    _uiState.update { interactor.checkState(resultState) }
                    updateState(ProgressState.COMPLETED)
                }
            }
        }
    }

    fun refresh() = with(viewModelScope){
        val lang = interactor.getCurrentLang()
        _uiState.update { BooksViewState.LoadingState(lang = lang) }
        viewModelScope.launch {
            interactor.getBooksFromServer()
            updateState(ProgressState.LOADING)
            initViewModel()
        }
    }

    fun updateFilters(subName: String){
        if(subName.isNotEmpty()){
            val current = uiState.value as BooksViewState.SuccessState
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

    fun updateSearchKeyword(keyword: String){
            val current = uiState.value as BooksViewState.SuccessState
            _uiState.update { current.copy(searchKeyWord = keyword) }
    }

    fun navigateToBook(
        navController: NavController,
        bookId: Int,
        bookDestination: String,
        prText: String,
        maxSalePercent: Int,
        bookSubscribeText: String,
        bookSubscribeMinCost: Int,
        bookSubscribeLink: String){
        val route = "$bookDestination/{bookId}/{prText}/{maxSalePercent}/{bookSubscribeText}/{bookSubscribeMinCost}/{bookSubscribeLink}"
        navController.navigate(
            route
                .replace(
                oldValue = "{bookId}",
                newValue = bookId.toString())
                .replace(
                oldValue = "{prText}",
                newValue = prText)
                .replace(
                oldValue = "{maxSalePercent}",
                newValue = maxSalePercent.toString())
                .replace(
                oldValue = "{bookSubscribeText}",
                newValue = bookSubscribeText)
                .replace(
                oldValue = "{bookSubscribeMinCost}",
                newValue = bookSubscribeMinCost.toString())
                .replace(
                oldValue = "{bookSubscribeLink}",
                newValue = bookSubscribeLink.replace("//", "**")
                )
            )
        }

    fun onClickLink(url: String){
        interactor.openInBrowser(url)
    }
}