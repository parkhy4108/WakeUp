package com.dev_musashi.wakeup.common

import android.util.Log

const val ARG_KEY = "problemCnt"

sealed class Screen(val route : String) {
    object Splash : Screen(route = "SPLASH")
    object Problem : Screen(route = "PROBLEM/{$ARG_KEY}")
}