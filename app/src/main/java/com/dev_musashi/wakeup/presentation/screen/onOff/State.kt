package com.dev_musashi.wakeup.presentation.screen.onOff

data class State(
    val time : String = "",
    val minute : String? = null,
    val second : String? = null,
    val isTimerClicked: Boolean = false,
    val isDialogShowed: Boolean = false
)
