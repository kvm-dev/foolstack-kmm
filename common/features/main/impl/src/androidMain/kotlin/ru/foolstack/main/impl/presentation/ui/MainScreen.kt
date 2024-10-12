package ru.foolstack.main.impl.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
import ru.foolstack.main.impl.mapper.Mapper
import ru.foolstack.main.impl.presentation.viewmodel.MainViewModel
import ru.foolstack.model.ProgressState
import ru.foolstack.profile.api.model.ProfileDomain
import ru.foolstack.ui.components.EventSlider
import ru.foolstack.ui.components.MainTopBar
import ru.foolstack.ui.components.UserType
import ru.foolstack.ui.model.Lang
import ru.foolstack.utils.model.ResultState

@Composable
fun MainScreen(mainViewModel: MainViewModel = koinViewModel()) {
    val viewModelState by mainViewModel.progressState.collectAsState()
    when(viewModelState) {
        ProgressState.COMPLETED -> {
            Log.d("realy Complete", "yes")
            val profileState by mainViewModel.profileState.collectAsState()
            when(profileState){
                is ResultState.Idle-> {
                    Log.d("state is", "idle")
                }
                is ResultState.Loading-> {
                    Log.d("state is", "Loading")
                }
                is ResultState.Success->{
                    val authStatus by mainViewModel.uiState.collectAsState()
                    when(authStatus){
                      is MainViewState.AuthorizedClient-> {
                          Column {
                              MainTopBar(userType = UserType.CLIENT, userName = (authStatus as MainViewState.AuthorizedClient).profile?.userName?:"", lang = Lang.RU)
                              EventSlider(lang = Lang.RU, events = Mapper().map((authStatus as MainViewState.AuthorizedClient).events))
                          }
                      }
                      is MainViewState.GuestClient-> {
                          Column {
                              MainTopBar(userType = UserType.CLIENT, lang = Lang.RU)
                              EventSlider(lang = Lang.RU, events = Mapper().map((authStatus as MainViewState.GuestClient).events))
                          }
                      }

                      is MainViewState.Loading-> {

                      }

                      is MainViewState.ErrorState-> {

                      }
                    }
                    Log.d("state is", "success, userName is ${(profileState as ResultState.Success<ProfileDomain>).data?.userName}")
                }
            }
        }
        else->{
            Log.d("realy complete", "no")
            mainViewModel.initViewModel()

        }
    }
}
