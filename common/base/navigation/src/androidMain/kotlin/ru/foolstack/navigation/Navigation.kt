package ru.foolstack.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.foolstack.authorization.impl.presentation.ui.StartAnotherScreen
import ru.foolstack.impl.presentation.ui.SplashScreen


@Composable
fun StartApplication(
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = NavigationScreens.valueOf(
        backStackEntry?.destination?.route ?: NavigationScreens.SplashScreenNavigation.name
    )
    NavHost(
        navController = navController,
        startDestination = NavigationScreens.SplashScreenNavigation.name,
        modifier = Modifier
            .fillMaxSize()
    ) {
        composable(route = NavigationScreens.SplashScreenNavigation.name) {
            SplashScreen(
                authScreen = {
                    navController.navigate(NavigationScreens.SplashScreenNavigation.name)
                }
            )
        }
        composable(route = NavigationScreens.AuthorizationScreenNavigation.name) {
            StartAnotherScreen(
                onNextButtonClicked = {
                    cancelOrderAndNavigateToStart(
                        navController
                    )
                }
            )
        }
    }
}

fun cancelOrderAndNavigateToStart(
    navController: NavHostController
) {
    navController.popBackStack(NavigationScreens.SplashScreenNavigation.name, inclusive = false)
}