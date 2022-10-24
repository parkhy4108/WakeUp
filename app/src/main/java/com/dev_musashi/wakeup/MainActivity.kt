package com.dev_musashi.wakeup

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dev_musashi.wakeup.common.ARG_KEY
import com.dev_musashi.wakeup.common.BottomBarScreen
import com.dev_musashi.wakeup.common.BottomNavigationBar
import com.dev_musashi.wakeup.common.Screen
import com.dev_musashi.wakeup.presentation.onOff.OnOffScreen
import com.dev_musashi.wakeup.presentation.problem.ProblemScreen
import com.dev_musashi.wakeup.presentation.setting.SettingScreen
import com.dev_musashi.wakeup.presentation.splash.SplashScreen
import com.dev_musashi.wakeup.ui.theme.WakeUpTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WakeUpTheme {
                val appState = rememberAppState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        bottomBar = {
                            if (appState.shouldShowBottomBar) {
                                BottomNavigationBar(
                                    tabs = appState.bottomBarTabs,
                                    currentRoute = appState.currentRoute!!,
                                    navigateToRoute = appState::navigateToBottomBarRoute,
                                    modifier = Modifier
                                )
                            }
                        },
                        snackbarHost = {
                            SnackbarHost(
                                hostState = it,
                                modifier = Modifier,
                                snackbar = { snackBarData ->
                                    Snackbar(snackBarData)
                                }
                            )
                        },
                        scaffoldState = appState.scaffoldState
                    ) { paddingValues ->
                        NavHost(
                            navController = appState.navController,
                            startDestination = Screen.Splash.route,
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            graph(appState = appState)
                        }
                    }
                }
            }
        }
    }
}

fun NavGraphBuilder.graph(appState: AppState) {
    composable(route = Screen.Splash.route) {
        SplashScreen(startBottomBar = { route -> appState.startBottomBarDestination(route) })
    }
    composable(route = BottomBarScreen.OnOff.route) {
        OnOffScreen(open = {route -> appState.navigate(route)})
    }
    composable(route = BottomBarScreen.Setting.route) {
        SettingScreen()
    }
    composable(
        route = Screen.Problem.route
    ) {
        ProblemScreen(popUp= {appState.popUp()})
    }
}

