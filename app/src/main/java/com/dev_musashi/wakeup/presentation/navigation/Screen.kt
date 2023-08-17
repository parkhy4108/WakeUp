package com.dev_musashi.wakeup.presentation.navigation

const val ARG_KEY = "problemCnt"

sealed class Screen(val route : String) {
    object Problem : Screen(route = "PROBLEM/{$ARG_KEY}")
}