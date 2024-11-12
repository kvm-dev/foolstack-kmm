package ru.foolstack.main.impl.domain.interactor


import ru.foolstack.authorization.api.domain.usecase.GetTokenFromLocalUseCase
import ru.foolstack.events.api.domain.usecase.GetEventsUseCase
import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.main.impl.presentation.ui.MainViewState
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.profile.api.domain.usecase.GetProfileUseCase
import ru.foolstack.profile.api.model.ProfileDomain

class MainInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val getTokenFromLocalUseCase: GetTokenFromLocalUseCase,
    private val getEventsUseCase: GetEventsUseCase){
    val eventsState = getEventsUseCase.eventsState

    val profileState = getProfileUseCase.profileState

    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()
    suspend fun getEventsFromServer() = getEventsUseCase.getEvents()
    suspend fun getEventsFromLocal() = getEventsUseCase.getEvents(fromLocal = true)

    suspend fun getProfileFromServer() = getProfileUseCase.getProfile()
    suspend fun getProfileFromLocal() = getProfileUseCase.getProfile(fromLocal = true)

    suspend fun isTokenExist() = getTokenFromLocalUseCase.getToken().isNotEmpty()

    fun checkState(
        profile: ProfileDomain,
        events: EventsDomain,
    ):MainViewState{
        val lang = getCurrentLang()
        val isConnected = isConnectionAvailable()
        return if(profile.userName.isNotEmpty()){
            MainViewState.AuthorizedClient(
                isHaveConnection = isConnected,
                lang = lang,
                profile = profile,
                events = events
            )
        } else MainViewState.GuestClient(
            isHaveConnection = isConnected,
            lang = lang,
            events = events
        )
    }

}