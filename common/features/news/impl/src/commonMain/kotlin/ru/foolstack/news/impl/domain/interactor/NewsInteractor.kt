package ru.foolstack.news.impl.domain.interactor

import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.news.api.domain.usecase.GetNewsUseCase
import ru.foolstack.news.api.model.NewsDomain
import ru.foolstack.news.impl.presentation.ui.NewsViewState
import ru.foolstack.utils.model.ResultState

class NewsInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getNewsUseCase: GetNewsUseCase
){
    val newsState = getNewsUseCase.newsState

    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()

    suspend fun getNewsFromServer() = getNewsUseCase.getNews()

    suspend fun getNewsFromLocal() = getNewsUseCase.getNews(fromLocal = true)

    fun checkState(state: ResultState<NewsDomain>):NewsViewState{
        val lang = getCurrentLang()
        when(state){
            is ResultState.Loading->{
                return NewsViewState.LoadingState(lang = lang)
            }
            is ResultState.Idle->{
                return NewsViewState.LoadingState(lang = lang)
            }
            is ResultState.Success->{
                return if(state.data?.errorMsg?.isNotEmpty() == true){
                    NewsViewState.ErrorState(lang = lang)
                } else{
                    NewsViewState.SuccessState(isHaveConnection = isConnectionAvailable(), news = state.data, lang = lang)
                }
            }
        }
    }
}