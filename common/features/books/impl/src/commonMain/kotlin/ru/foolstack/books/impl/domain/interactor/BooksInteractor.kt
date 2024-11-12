package ru.foolstack.books.impl.domain.interactor

import ru.foolstack.books.api.domain.usecase.GetBooksUseCase
import ru.foolstack.books.api.model.BooksDomain
import ru.foolstack.books.impl.presentation.ui.BooksViewState
import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.utils.BrowserUtils
import ru.foolstack.utils.model.ResultState

class BooksInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getBooksUseCase: GetBooksUseCase,
    private val browserUtils: BrowserUtils
){
    val booksState = getBooksUseCase.booksState

    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    private fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()

    suspend fun getBooksFromServer() = getBooksUseCase.getBooks()

    fun checkState(state: ResultState<BooksDomain>):BooksViewState{
        val lang = getCurrentLang()
        when(state){
            is ResultState.Loading->{
                return BooksViewState.LoadingState(lang = lang)
            }
            is ResultState.Idle->{
                return BooksViewState.LoadingState(lang = lang)
            }
            is ResultState.Success->{
                return if(state.data?.errorMsg?.isNotEmpty() == true){
                    BooksViewState.ErrorState(lang = lang)
                } else{
                    val filtersList = HashSet<String>()
                    state.data?.books?.forEach { book->
                        book.professions.forEach { sub->
                            filtersList.add(sub.professionName)
                        }
                    }
                    BooksViewState.SuccessState(isHaveConnection = isConnectionAvailable(), books = state.data, selectedFilters = filtersList.toList(), lang = lang)
                }
            }
        }
    }

    fun openInBrowser(url: String){
        browserUtils.openInBrowser(url)
    }
}