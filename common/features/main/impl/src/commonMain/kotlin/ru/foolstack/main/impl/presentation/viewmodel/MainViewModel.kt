package ru.foolstack.main.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.foolstack.main.impl.domain.interactor.MainInteractor
import ru.foolstack.main.impl.presentation.ui.MainViewState
import ru.foolstack.model.ProgressState
import ru.foolstack.profile.api.model.ProfileDomain
import ru.foolstack.utils.model.ResultState
import ru.foolstack.viewmodel.BaseViewModel


class MainViewModel(private val interactor: MainInteractor) : BaseViewModel() {
    private val _uiState = MutableStateFlow<MainViewState>(
        MainViewState.Loading
    )
    private val _profileState = MutableStateFlow<ResultState<ProfileDomain>>(
        ResultState.Loading
    )
    val uiState: StateFlow<MainViewState> = _uiState.asStateFlow()
    val profileState: StateFlow<ResultState<ProfileDomain>> = _profileState.asStateFlow()

    fun initViewModel() = with(viewModelScope + coroutineExceptionHandler) {
        if(progressState.value == ProgressState.LOADING){
        launch {
            interactor.profileState.combine(interactor.eventsState) { profile, events  ->
                if(profile is ResultState.Success && events is ResultState.Success){
                    _profileState.value = profile
                    val profileData = profile.data
                    val eventsData = events.data
                    if(profileData !=null && eventsData !=null){
                        _uiState.update { interactor.checkState(profile = profileData, events = eventsData) }
                    }
                    else{
                        //nothing
                    }
                    updateState(ProgressState.COMPLETED)
            }
                else{
                    //show Dialog error and go to Authorization
                }
            }.collect()
        }
        }
    }

    fun navigateToEvent(navController: NavController, eventId: Int, eventDestination: String){
        val route = "$eventDestination/{eventId}"
        navController.navigate(
            route.replace(
                oldValue = "{eventId}",
                newValue = eventId.toString()
            )
        )
    }

}