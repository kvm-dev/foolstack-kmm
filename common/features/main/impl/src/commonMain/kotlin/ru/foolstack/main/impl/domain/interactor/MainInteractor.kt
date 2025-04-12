package ru.foolstack.main.impl.domain.interactor


import ru.foolstack.asmode.api.domain.usecase.GetAsModeUseCase
import ru.foolstack.authorization.api.domain.usecase.GetTokenFromLocalUseCase
import ru.foolstack.books.api.domain.usecase.GetBooksUseCase
import ru.foolstack.events.api.domain.usecase.GetEventsUseCase
import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.main.impl.data.StringResources
import ru.foolstack.main.impl.presentation.ui.MainViewState
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.news.api.domain.usecase.GetNewsUseCase
import ru.foolstack.profile.api.domain.usecase.GetProfileUseCase
import ru.foolstack.profile.api.domain.usecase.LogoutUseCase
import ru.foolstack.profile.api.model.ProfileDomain
import ru.foolstack.study.api.domain.usecase.GetStudiesUseCase
import ru.foolstack.utils.model.ResultState

class MainInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getTokenFromLocalUseCase: GetTokenFromLocalUseCase,
    private val getEventsUseCase: GetEventsUseCase,
    private val getAsModeUseCase: GetAsModeUseCase
  ){
    val eventsState = getEventsUseCase.eventsState
    val profileState = getProfileUseCase.profileState

    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()
    suspend fun getEventsFromServer() = getEventsUseCase.getEvents()
    suspend fun getEventsFromLocal() = getEventsUseCase.getEvents(fromLocal = true)

    suspend fun getProfileFromServer() = getProfileUseCase.getProfile()
    suspend fun getProfileFromLocal() = getProfileUseCase.getProfile(fromLocal = true)

    fun checkState(eventsState: ResultState<EventsDomain>, profileState: ResultState<ProfileDomain>?):MainViewState{
        val lang = getCurrentLang()
        when(eventsState){
            is ResultState.Loading->{
                return MainViewState.Loading(lang = lang)
            }
            is ResultState.Idle->{
                return MainViewState.Loading(lang = lang)
            }
            is ResultState.Success->{
                return if(eventsState.data?.errorMsg?.isNotEmpty() == true){
                    MainViewState.ErrorState(errorTitle = if(lang is LangResultDomain.Ru){ "" } else { "" },
                        errorText = if(lang is LangResultDomain.Ru){ "" } else { "" }
                    )
                } else{
                    if(profileState!=null){
                        when(profileState){
                            is ResultState.Idle -> {
                                return MainViewState.Loading(lang = lang)
                            }
                            is ResultState.Loading -> {
                                return MainViewState.Loading(lang = lang)
                            }
                            is ResultState.Success -> {
                                if(profileState.data?.userName?.isNotEmpty() == true){
                                    return MainViewState.AuthorizedClient(
                                        isHaveConnection = isConnectionAvailable(),
                                        lang = lang,
                                        profile = profileState.data,
                                        events = eventsState.data
                                    )
                                }
                                else{
                                    return MainViewState.ErrorState(errorTitle = if(lang is LangResultDomain.Ru){ "" } else { "" },
                                        errorText = if(lang is LangResultDomain.Ru){ "" } else { "" }
                                    )
                                }

                            }
                        }
                    }
                    else{
                        return MainViewState.ErrorState(errorTitle = if(lang is LangResultDomain.Ru){ "" } else { "" },
                            errorText = if(lang is LangResultDomain.Ru){ "" } else { "" }
                        )
                    }
                }
            }
        }
    }

    suspend fun logout(){
        logoutUseCase.logout()
        getProfileUseCase.clearState()
    }

    fun getQuitDialogTitle():String{
        return StringResources.getQuitDialogTitle(getCurrentLanguageUseCase.getCurrentLang().lang)
    }

    fun getQuitDialogDescription():String{
        return StringResources.getQuitDialogDescription(getCurrentLanguageUseCase.getCurrentLang().lang)
    }
    fun getQuitDialogMainBtn():String{
        return StringResources.getQuitDialogMainBtn(getCurrentLanguageUseCase.getCurrentLang().lang)
    }

    fun getQuitDialogSecondBtn():String{
        return StringResources.getQuitDialogSecondBtn(getCurrentLanguageUseCase.getCurrentLang().lang)
    }

    suspend fun isAsModeActive():Boolean{
        return getAsModeUseCase.isAsModeEnabled(isConnectionAvailable()).isAsModeActive
    }
}