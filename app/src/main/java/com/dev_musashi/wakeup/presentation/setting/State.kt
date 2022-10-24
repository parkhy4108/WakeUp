package com.dev_musashi.wakeup.presentation.setting

data class State(
    val time: Int = 3,
    val showDialog: Boolean = false,
    val showRingtoneDialog: Boolean = false,
    val problemCnt: Int = 3,
    val soundSwitch: Boolean = false,
    val vibrationSwitch: Boolean = false,
    val earPhoneSwitch: Boolean = false,
    val problemSwitch: Boolean = false,
    val ringtoneList: List<Pair<String, String>> = emptyList(),
    val radioSelected : String = "",
    val ringtoneTitle: String = "미지정"
)
