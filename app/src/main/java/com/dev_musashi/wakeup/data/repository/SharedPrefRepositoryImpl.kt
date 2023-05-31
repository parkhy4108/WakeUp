package com.dev_musashi.wakeup.data.repository

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.dev_musashi.wakeup.domain.Setting
import com.dev_musashi.wakeup.domain.SharedPrefRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import dagger.hilt.android.qualifiers.ApplicationContext

class SharedPrefRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context
) : SharedPrefRepository {

    private val Context.dataStore by preferencesDataStore(name = "setting")
    private val timeKey = intPreferencesKey("time")
    private val soundKey = booleanPreferencesKey("sound")
    private val vibratorKey = booleanPreferencesKey("vibrator")
    private val problemKey = booleanPreferencesKey("problem")
    private val problemCntKey = intPreferencesKey("problemCnt")
    private val ringtoneTitleKey = stringPreferencesKey("ringtoneTitle")
    private val ringtoneUriKey = stringPreferencesKey("ringToneUri")

    override suspend fun getSetting(): Flow<Setting> {
        return context.dataStore.data
            .catch { exception ->
                if(exception is IOException)
                    emit(emptyPreferences())
                else throw exception
            }
            .map { pref ->
                val time = pref[timeKey]?: 0
                val sound = pref[soundKey]?: false
                val vibration = pref[vibratorKey]?: false
                val problem = pref[problemKey]?: false
                val problemCnt = pref[problemCntKey]?: 3
                val ringtoneTitle = pref[ringtoneTitleKey]?: ""
                val ringtoneUri = pref[ringtoneUriKey]?: "미지정"

                Setting(
                    time,
                    problem,
                    problemCnt,
                    sound,
                    vibration,
                    ringtoneTitle,
                    ringtoneUri
                )
            }
    }

    override suspend fun setSettingValue(
        time: Int,
        problem: Boolean,
        problemCnt: Int,
        sound: Boolean,
        vibration: Boolean,
        ringtoneTitle: String,
        ringtoneUri: String
    ) {
        context.dataStore.edit {
            it[timeKey] = time
            it[problemKey] = problem
            it[problemCntKey] = problemCnt
            it[soundKey] = sound
            it[vibratorKey] = vibration
            it[ringtoneTitleKey] = ringtoneTitle
            it[ringtoneUriKey] = ringtoneUri
        }
    }
}
