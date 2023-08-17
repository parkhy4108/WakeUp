package com.dev_musashi.wakeup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.dev_musashi.wakeup.presentation.navigation.BottomBarScreen
import com.dev_musashi.wakeup.presentation.navigation.BottomNavigationBar
import com.dev_musashi.wakeup.presentation.navigation.graph
import com.dev_musashi.wakeup.presentation.ui.theme.WakeUpTheme

@Composable
fun MainContent(){
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
                    startDestination = BottomBarScreen.OnOff.route,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    graph(appState = appState)
                }
            }
        }
    }
}