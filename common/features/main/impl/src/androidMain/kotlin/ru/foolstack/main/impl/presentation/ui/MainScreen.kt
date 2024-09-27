package ru.foolstack.main.impl.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel
import ru.foolstack.events.api.model.EventsDomain
import ru.foolstack.main.impl.mapper.Mapper
import ru.foolstack.main.impl.presentation.viewmodel.MainViewModel
import ru.foolstack.ui.components.EventSlider
import ru.foolstack.ui.components.MainTopBar
import ru.foolstack.ui.components.UserType
import ru.foolstack.ui.model.EventItem
import ru.foolstack.ui.model.Lang

@Composable
fun MainScreen(mainViewModel: MainViewModel = koinViewModel()) {
    var listEvents = ArrayList<EventItem>()
    //закинуть во вью модель и интерактор
//    if(events!=null){
//        if(events.errorMsg.isNotEmpty()){
//            //showError
//        }
//        else{
//           listEvents.addAll(Mapper().map(events))
//           }
//        }
//    Column {
//        MainTopBar(userType = UserType.CLIENT, lang = Lang.RU)
//        EventSlider(lang = Lang.RU, listEvents)
//    }
}
