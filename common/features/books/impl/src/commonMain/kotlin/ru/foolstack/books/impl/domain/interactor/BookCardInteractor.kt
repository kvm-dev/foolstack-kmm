package ru.foolstack.books.impl.domain.interactor

import ru.foolstack.books.api.domain.usecase.GetBooksUseCase
import ru.foolstack.books.api.model.BooksDomain
import ru.foolstack.books.impl.presentation.ui.BookCardViewState
import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.utils.BrowserUtils
import ru.foolstack.utils.model.ResultState

class BookCardInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    getBooksUseCase: GetBooksUseCase,
    private val browserUtils: BrowserUtils
){
    val booksState = getBooksUseCase.booksState

    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    private fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()

    fun checkState(state: ResultState<BooksDomain>, bookId: Int): BookCardViewState {
        val lang = getCurrentLang()
        return when(state){
            is ResultState.Loading->{
                BookCardViewState.LoadingState(lang = lang)
            }

            is ResultState.Idle->{
                BookCardViewState.LoadingState(lang = lang)
            }

            is ResultState.Success->{
                if(state.data?.errorMsg?.isNotEmpty() == true){
                    BookCardViewState.ErrorState(lang = lang)
                } else{
                    BookCardViewState.SuccessState(isHaveConnection = isConnectionAvailable(), book = state.data?.books?.find { it.bookId == bookId }, lang = lang)
                }
            }
        }
    }

    fun openInBrowser(url: String){
        browserUtils.openInBrowser(url)
    }
}