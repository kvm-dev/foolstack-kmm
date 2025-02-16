package ru.foolstack.main.impl.domain.interactor


import ru.foolstack.asmode.api.domain.usecase.GetAsModeUseCase
import ru.foolstack.asmode.api.model.AsModeDomain
import ru.foolstack.authorization.api.domain.usecase.GetTokenFromLocalUseCase
import ru.foolstack.books.api.domain.usecase.GetBooksUseCase
import ru.foolstack.events.api.domain.usecase.GetEventsUseCase
import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.interview.api.domain.usecase.GetMaterialsUseCase
import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.main.impl.presentation.ui.MainViewState
import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.news.api.domain.usecase.GetNewsUseCase
import ru.foolstack.profile.api.domain.usecase.GetProfileUseCase
import ru.foolstack.profile.api.domain.usecase.LogoutUseCase
import ru.foolstack.profile.api.model.ProfileDomain
import ru.foolstack.study.api.domain.usecase.GetStudiesUseCase
import ru.foolstack.tests.api.domain.usecase.GetTestsUseCase
import ru.foolstack.utils.model.ResultState

class MainInteractor(
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val getBooksUseCase: GetBooksUseCase,
    private val getStudiesUseCase: GetStudiesUseCase,
    private val getMaterialsUseCase: GetMaterialsUseCase,
    private val getTestsUseCase: GetTestsUseCase,
    private val getNewsUseCase: GetNewsUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getTokenFromLocalUseCase: GetTokenFromLocalUseCase,
    private val getEventsUseCase: GetEventsUseCase,
    private val getAsModeUseCase: GetAsModeUseCase){
    val eventsState = getEventsUseCase.eventsState

    val profileState = getProfileUseCase.profileState

    fun getCurrentLang() = getCurrentLanguageUseCase.getCurrentLang()

    fun isConnectionAvailable() = getNetworkStateUseCase.isNetworkAvailable()
    suspend fun getEventsFromServer() = getEventsUseCase.getEvents()
    suspend fun getEventsFromLocal() = getEventsUseCase.getEvents(fromLocal = true)

    suspend fun getProfileFromServer() = getProfileUseCase.getProfile()
    suspend fun getProfileFromLocal() = getProfileUseCase.getProfile(fromLocal = true)

    suspend fun isTokenExist() = getTokenFromLocalUseCase.getToken().isNotEmpty()

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
                                return MainViewState.GuestClient(
                                    isHaveConnection = isConnectionAvailable(),
                                    lang = lang,
                                    events = eventsState.data)
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
                                    return MainViewState.GuestClient(isHaveConnection = isConnectionAvailable(),
                                        lang = lang,
                                        events = eventsState.data)
                                }

                            }
                        }
                    }
                    else{
                        return MainViewState.GuestClient(isHaveConnection = isConnectionAvailable(),
                            lang = lang,
                            events = eventsState.data)
                    }
                }
            }
        }
    }

    suspend fun logout(){
        logoutUseCase.logout()
        getProfileUseCase.clearState()
    }

    fun getOnlyClientsDialogSettingsTitle():String{
        return if(getCurrentLanguageUseCase.getCurrentLang() is LangResultDomain.Ru){
            "Очень жаль"
        } else{
            "Sorry"
        }
    }

    fun getOnlyClientsDialogSettingsText():String{
        return if(getCurrentLanguageUseCase.getCurrentLang() is LangResultDomain.Ru){
            "Настройки доступны только авторизованным пользователям"
        } else{
            "Settings available only for authorized users"
        }
    }

    fun getDialogsOkBtn():String{
        return if(getCurrentLanguageUseCase.getCurrentLang() is LangResultDomain.Ru){
            "Ок"
        } else{
            "Ok"
        }
    }

    fun getGuestNotificationDialogTitle():String{
        return if(getCurrentLanguageUseCase.getCurrentLang() is LangResultDomain.Ru){
            "Обрати внимание"
        } else{
            "Attention"
        }
    }

    fun getGuestNotificationDialogText():String{
        return if(getCurrentLanguageUseCase.getCurrentLang() is LangResultDomain.Ru){
            "Неавторизованные пользователи видят контент в ограниченном виде и имеют меньше возможностей.\n\nВойди в учетную запись для того, чтобы пользоваться более широким функционалом приложения"
        } else{
            "Content is available to unauthorized users in a limited form.\n\nSign in to your account to access advanced app features"
        }
    }

    fun getGuestNotificationDialogActionBtn():String{
        return if(getCurrentLanguageUseCase.getCurrentLang() is LangResultDomain.Ru){
            "Авторизоваться"
        } else{
            "Sign in"
        }
    }

    fun getGuestNotificationDialogSecondBtn():String{
        return if(getCurrentLanguageUseCase.getCurrentLang() is LangResultDomain.Ru){
            "Продолжить как гость"
        } else{
            "Continue as guest"
        }
    }

    suspend fun isAsModeActive():Boolean{
        return if(isConnectionAvailable()){
            getAsModeUseCase.getAsMode().isAsModeActive
        } else{
            false
        }
    }

    suspend fun getAllData(){
        if(isConnectionAvailable()){
            getBooksUseCase.getBooks()
            getStudiesUseCase.getStudies()
            getMaterialsUseCase.getMaterials()
            getTestsUseCase.getTests()
            getNewsUseCase.getNews()
        }
    }
}