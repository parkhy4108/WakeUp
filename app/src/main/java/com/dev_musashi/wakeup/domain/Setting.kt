package com.dev_musashi.wakeup.domain

data class Setting(
    val time : Int,
    val problem : Boolean,
    val problemCnt: Int,
    val sound: Boolean,
    val vibration: Boolean,
    val ringtoneTitle: String,
    val ringtoneUri: String
)
