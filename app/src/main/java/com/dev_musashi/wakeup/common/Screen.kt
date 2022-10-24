package com.dev_musashi.wakeup.common

import android.util.Log

const val ARG_KEY = "problemCnt"

sealed class Screen(val route : String) {
    object Splash : Screen(route = "SPLASH")
    object Problem : Screen(route = "PROBLEM/{$ARG_KEY}")

//    object Problem : Screen(route = "PROBLEM/{$ARG_KEY}")

//    fun passProblemCnt(problemCnt: Int): String {
//        return this.route.replace(
//            oldValue = "{$ARG_KEY}",
//            newValue = problemCnt.toString()
//        )
//    }

}