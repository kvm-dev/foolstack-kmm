package ru.foolstack.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.inject
import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.splash.impl.presentation.ui.SplashScreen
import ru.foolstack.main.impl.presentation.ui.MainScreen
import ru.foolstack.ui.components.BottomAppBar
import ru.foolstack.ui.components.BottomIcons
import ru.foolstack.ui.theme.FoolStackTheme
import ru.foolstack.ui.theme.MainBackground


@Composable
fun StartApplication(
    navController: NavHostController = rememberNavController()) {
    FoolStackTheme {
        val getCurrentLanguageUseCase: GetCurrentLanguageUseCase by inject()
        // Get current back stack entry
        val backStackEntry by navController.currentBackStackEntryAsState()
        // Get the name of the current screen
        val currentScreen = NavigationScreens.valueOf(
            backStackEntry?.destination?.route ?: NavigationScreens.SplashScreenNavigation.name
        )
        val isShowBottomBar = remember { mutableStateOf(false) }
        val bottomBarSelectedState = remember { mutableStateOf(BottomIcons.MAIN) }
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    selectedState = bottomBarSelectedState,
                    isShow = isShowBottomBar,
                    lang = getCurrentLanguageUseCase.getCurrentLang().lang
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                NavHost(
                    navController = navController,
                    startDestination = NavigationScreens.SplashScreenNavigation.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.MainBackground)
                ) {
                    composable(route = NavigationScreens.SplashScreenNavigation.name) {
                        SplashScreen(
                            navigateToMainScreen = {
                                isShowBottomBar.value = true
                                navController.navigate(NavigationScreens.MainScreenNavigation.name) {
                                    popUpTo(NavigationScreens.SplashScreenNavigation.name) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }
                    composable(route = NavigationScreens.MainScreenNavigation.name) {
                        MainScreen()
                    }
                }
            }
        }
    }
}

fun cancelOrderAndNavigateToStart(
    navController: NavHostController
) {
    navController.popBackStack(NavigationScreens.SplashScreenNavigation.name, inclusive = false)
}
