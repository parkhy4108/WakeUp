package com.dev_musashi.wakeup.domain

import com.dev_musashi.wakeup.data.model.Setting
import kotlinx.coroutines.flow.Flow


interface SharedPrefRepository {

    suspend fun getSetting(): Flow<Setting>

    suspend fun setSettingValue(
        time: Int,
        problem: Boolean,
        problemCnt: Int,
        sound: Boolean,
        vibration: Boolean,
        ringtoneTitle: String,
        ringtoneUri: String
    )

}