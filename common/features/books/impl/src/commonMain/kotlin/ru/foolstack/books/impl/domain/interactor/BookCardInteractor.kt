package ru.foolstack.books.impl.domain.interactor

import ru.foolstack.asmode.api.domain.usecase.GetAsModeUseCase
import ru.foolstack.books.api.domain.usecase.GetBooksUseCase
import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.utils.BrowserUtils

class BookCardInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getAsModeUseCase: GetAsModeUseCase,
    getBooksUseCase: GetBooksUseCase,
    private val browserUtils: BrowserUtils
){
    val booksState = getBooksUseCase.booksState

    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()

    fun openInBrowser(url: String){
        browserUtils.openInBrowser(url)
    }

    suspend fun isAsModeActive():Boolean{
        return getAsModeUseCase.isAsModeEnabled(isConnectionAvailable()).isAsModeActive
    }
}