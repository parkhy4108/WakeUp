package com.dev_musashi.wakeup.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dev_musashi.wakeup.AppState
import com.dev_musashi.wakeup.presentation.screen.onOff.OnOffScreen
import com.dev_musashi.wakeup.presentation.screen.problem.ProblemScreen
import com.dev_musashi.wakeup.presentation.screen.setting.SettingScreen
fun NavGraphBuilder.graph(appState: AppState) {
    composable(route = BottomBarScreen.OnOff.route) {
        OnOffScreen(open = {route -> appState.navigate(route)})
    }
    composable(route = BottomBarScreen.Setting.route) {
        SettingScreen()
    }
    composable(route = Screen.Problem.route) {
        ProblemScreen(popUp= {appState.popUp()})
    }
}