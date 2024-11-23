package ru.foolstack.navigation

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.inject
import ru.foolstack.books.impl.presentation.ui.BookCardScreen
import ru.foolstack.books.impl.presentation.ui.BooksScreen
import ru.foolstack.events.impl.presentation.ui.EventsScreen
import ru.foolstack.events.impl.presentation.ui.EventCardScreen
import ru.foolstack.interview.impl.presentation.ui.InterviewCardScreen
import ru.foolstack.interview.impl.presentation.ui.InterviewsScreen
import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.splash.impl.presentation.ui.SplashScreen
import ru.foolstack.main.impl.presentation.ui.MainScreen
import ru.foolstack.news.impl.presentation.ui.NewsCardScreen
import ru.foolstack.news.impl.presentation.ui.NewsScreen
import ru.foolstack.professions.impl.presentation.ui.ProfessionsScreen
import ru.foolstack.study.impl.presentation.ui.StudiesScreen
import ru.foolstack.ui.components.BottomAppBar
import ru.foolstack.ui.components.BottomIcons
import ru.foolstack.ui.theme.FoolStackTheme
import ru.foolstack.ui.theme.MainBackground


@Composable
fun StartApplication(
    navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val activity = context.findActivity()
    FoolStackTheme {
        val getCurrentLanguageUseCase: GetCurrentLanguageUseCase by inject()
        // Get current back stack entry
        // Get the name of the current screen
        val isShowBottomBar = remember { mutableStateOf(false) }
        val bottomBarSelectedState = remember { mutableStateOf(BottomIcons.MAIN) }
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    selectedState = bottomBarSelectedState,
                    isShow = isShowBottomBar,
                    lang = getCurrentLanguageUseCase.getCurrentLang().lang,
                    onClickMain = {
                        isShowBottomBar.value = true
                        navController.navigate(NavigationScreens.MainScreenNavigation.name) {
                            launchSingleTop = true
                        }
                    },
                    onClickNews = {
                        isShowBottomBar.value = true
                        navController.navigate(NavigationScreens.NewsListScreenNavigation.name) {
                            launchSingleTop = true
                        }
                    },
                    onClickInterviews = {
                        isShowBottomBar.value = true
                        navController.navigate(NavigationScreens.InterviewsListScreenNavigation.name) {
                            launchSingleTop = true
                        }
                    }
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
                        isShowBottomBar.value = true
                        bottomBarSelectedState.value = BottomIcons.MAIN
                        BackHandler(onBack = { activity?.finish()})
                        MainScreen(onClickEvents = {
                            isShowBottomBar.value = false
                            navController.navigate(NavigationScreens.EventsListScreenNavigation.name) {
                                popUpTo(NavigationScreens.EventsListScreenNavigation.name) {
                                    inclusive = false
                                }
                            }
                        },
                            onClickBooks = {
                                isShowBottomBar.value = false
                                navController.navigate(NavigationScreens.BooksListScreenNavigation.name) {
                                    popUpTo(NavigationScreens.BooksListScreenNavigation.name) {
                                        inclusive = false
                                    }
                                }
                            },
                            onclickStudies = {
                                isShowBottomBar.value = false
                                navController.navigate(NavigationScreens.StudiesListScreenNavigation.name) {
                                    popUpTo(NavigationScreens.StudiesListScreenNavigation.name) {
                                        inclusive = false
                                    }
                                }
                            },
                            navController = navController, eventDestination = NavigationScreens.EventScreenNavigation.name )
                    }

                    composable(route = NavigationScreens.EventsListScreenNavigation.name) {
                        isShowBottomBar.value = false
                        EventsScreen(navController = navController, eventDestination = NavigationScreens.EventScreenNavigation.name)
                    }

                    composable(route = "${NavigationScreens.EventScreenNavigation.name}/{eventId}") {
                            navBackStackEntry ->
                        val eventId = navBackStackEntry.arguments?.getString("eventId")
                        eventId?.let { id->
                            isShowBottomBar.value = false
                            EventCardScreen(eventId = id.toInt())
                        }
                    }

                    composable(route = NavigationScreens.BooksListScreenNavigation.name) {
                        isShowBottomBar.value = false
                        BooksScreen(navController = navController, bookDestination = NavigationScreens.BookScreenNavigation.name)
                    }

                    composable(route = "${NavigationScreens.BookScreenNavigation.name}/{bookId}/{prText}/{maxSalePercent}/{bookSubscribeText}/{bookSubscribeMinCost}/{bookSubscribeLink}") {
                            navBackStackEntry ->
                        val bookId = navBackStackEntry.arguments?.getString("bookId")
                        val prText = navBackStackEntry.arguments?.getString("prText")?:""
                        val maxSalePercent = navBackStackEntry.arguments?.getString("maxSalePercent")?:"0"
                        val bookSubscribeText = navBackStackEntry.arguments?.getString("bookSubscribeText")?:""
                        val bookSubscribeMinCost = navBackStackEntry.arguments?.getString("bookSubscribeMinCost")?:"0"
                        val bookSubscribeLink = navBackStackEntry.arguments?.getString("bookSubscribeLink")?.replace("**", "//")?:""
                        bookId?.let { id->
                            isShowBottomBar.value = false
                            BookCardScreen(
                                bookId = id.toInt(),
                                prText = prText,
                                maxSalePercent = maxSalePercent.toInt(),
                                bookSubscribeText = bookSubscribeText,
                                bookSubscribeMinCost = bookSubscribeMinCost.toInt(),
                                bookSubscribeLink = bookSubscribeLink
                                )
                        }
                    }

                    composable(route = NavigationScreens.StudiesListScreenNavigation.name) {
                        isShowBottomBar.value = false
                        StudiesScreen(navController = navController)
                    }

                    composable(route = NavigationScreens.NewsListScreenNavigation.name) {
                        isShowBottomBar.value = true
                        bottomBarSelectedState.value = BottomIcons.NEWS
                        NewsScreen(navController = navController, newsDestination = NavigationScreens.NewsScreenNavigation.name)
                    }

                    composable(route = "${NavigationScreens.NewsScreenNavigation.name}/{newsId}") {
                            navBackStackEntry ->
                        val newsId = navBackStackEntry.arguments?.getString("newsId")
                        newsId?.let { id->
                            isShowBottomBar.value = false
                            NewsCardScreen(newsId = id.toInt())
                        }
                    }

                    composable(route = NavigationScreens.InterviewsListScreenNavigation.name) {
                        isShowBottomBar.value = true
                        bottomBarSelectedState.value = BottomIcons.INTERVIEW
                        InterviewsScreen(navController = navController,
                            interviewDestination = NavigationScreens.InterviewScreenNavigation.name,
                            selectProfession = {
                                isShowBottomBar.value = false
                                navController.navigate(NavigationScreens.ProfessionsListScreenNavigation.name) {
                                    popUpTo(NavigationScreens.ProfessionsListScreenNavigation.name) {
                                        inclusive = false
                                    }
                                }
                            })
                    }

                    composable(route = "${NavigationScreens.InterviewScreenNavigation.name}/{materialId}") {
                            navBackStackEntry ->
                        val materialId = navBackStackEntry.arguments?.getString("materialId")
                        materialId?.let { id->
                            isShowBottomBar.value = false
                            InterviewCardScreen(materialId = id.toInt())
                        }
                    }

                    composable(route = NavigationScreens.ProfessionsListScreenNavigation.name) {
                        isShowBottomBar.value = false
                        ProfessionsScreen(navController = navController, navigateToMain = { cancelOrderAndNavigateToStart(navController) })
                    }
                }
            }
        }
    }
}

fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun cancelOrderAndNavigateToStart(
    navController: NavHostController
) {
    navController.popBackStack(NavigationScreens.MainScreenNavigation.name, inclusive = false)
}
