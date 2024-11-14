package ru.foolstack.news.impl.domain.interactor

import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.news.api.domain.usecase.GetNewsUseCase
import ru.foolstack.utils.ShareUtils

class NewsCardInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    getNewsUseCase: GetNewsUseCase,
    private val shareUtils: ShareUtils
){
    val newsState = getNewsUseCase.newsState

    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()

    fun shareLink(url: String){
        shareUtils.shareLink(url)
    }
}