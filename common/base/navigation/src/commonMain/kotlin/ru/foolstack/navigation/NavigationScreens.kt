package ru.foolstack.navigation

import ru.foolstack.events.api.model.EventsDomain

enum class NavigationScreens(val title: String) {
    SplashScreenNavigation( "SplashScreen"),
    MainScreenNavigation("Main"),
    InterviewScreen("Interview"),
    TestsScreen("Tests"),
    NewsScreen("News")
}