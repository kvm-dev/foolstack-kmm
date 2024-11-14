package ru.foolstack.events.impl.domain.interactor

import ru.foolstack.events.api.domain.usecase.GetEventsUseCase
import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.utils.BrowserUtils

class EventCardInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    getEventsUseCase: GetEventsUseCase,
    private val browserUtils: BrowserUtils
){
    val eventsState = getEventsUseCase.eventsState

    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()

    fun openInBrowser(url: String){
        browserUtils.openInBrowser(url)
    }
}