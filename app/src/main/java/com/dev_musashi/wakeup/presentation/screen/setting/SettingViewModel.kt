package com.dev_musashi.wakeup.presentation.screen.setting

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_musashi.wakeup.R
import com.dev_musashi.wakeup.domain.RingtoneService
import com.dev_musashi.wakeup.domain.SharedPrefRepository
import com.dev_musashi.wakeup.presentation.util.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val sharedPrefRepository: SharedPrefRepository,
    private val ringtoneService: RingtoneService

) : ViewModel() {
    var state = mutableStateOf(State())
        private set

    private val time get() = state.value.time
    private val sound get() = state.value.soundSwitch
    private val vibration get() = state.value.vibrationSwitch
    private val problem get() = state.value.problemSwitch
    private val problemCnt get() = state.value.problemCnt

    private var tempTitle = ""
    private var tempUri = ""

    private var finalTitle = ""
    private var finalUri = ""

    fun initSetting() {
        viewModelScope.launch(Dispatchers.IO) {
            sharedPrefRepository.getSetting().collectLatest {
                state.value = state.value.copy(
                    time = it.time.toString(),
                    soundSwitch = it.sound,
                    vibrationSwitch = it.vibration,
                    problemSwitch = it.problem,
                    problemCnt = it.problemCnt,
                    ringtoneTitle = it.ringtoneTitle
                )
            }
        }
        val ringtoneList = ringtoneService.getRingtoneList()
        state.value = state.value.copy(ringtoneList = ringtoneList)
    }

    fun showDialog() {
        state.value = state.value.copy(showDialog = true)
    }

    fun onConfirmClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            sharedPrefRepository.setSettingValue(
                time = time.toInt(),
                problem = problem,
                problemCnt = problemCnt,
                sound = sound,
                vibration = vibration,
                ringtoneTitle = finalTitle,
                ringtoneUri = finalUri
            )
            state.value = state.value.copy(showDialog = false)
            SnackBarManager.showMessage(R.string.saveDone)
        }

    }

    fun onDialogCancel() {
        state.value = state.value.copy(showDialog = false)
    }

    fun onTimeChanged(newTime: String) {
        state.value = state.value.copy(time = newTime)
    }

    fun problemPlus() {
        if (problemCnt + 1 > 5) {
            SnackBarManager.showMessage(R.string.problemMax)
        } else {
            state.value = state.value.copy(problemCnt = problemCnt + 1)
        }
    }

    fun problemMinus() {
        if (problemCnt - 1 <= 0) {
            SnackBarManager.showMessage(R.string.problemMin)
        } else {
            state.value = state.value.copy(problemCnt = problemCnt - 1)
        }
    }

    fun soundSwitch() {
        state.value = state.value.copy(soundSwitch = !sound)
    }

    fun vibrationSwitch() {
        state.value = state.value.copy(vibrationSwitch = !vibration)
    }

    fun problemSwitch() {
        state.value = state.value.copy(problemSwitch = !problem)
    }

    fun showRingtoneDialog() {
        state.value = state.value.copy(showRingtoneDialog = true)
    }

    fun clickRingtoneItem(item: Pair<String,String>) {
        tempTitle = item.first
        tempUri = item.second
        ringtoneService.cancel()
        state.value = state.value.copy(
            radioSelected = item.first
        )
        ringtoneService.ringSelectedRingtone(item.second)
    }

    fun onDismissRingtoneDialog(){
        ringtoneService.cancel()
        state.value = state.value.copy(showRingtoneDialog = false)
    }

    fun onConfirmRingtoneDialog(){
        finalTitle = tempTitle
        finalUri = tempUri
        state.value = state.value.copy(ringtoneTitle = finalTitle)
        onDismissRingtoneDialog()
    }
}