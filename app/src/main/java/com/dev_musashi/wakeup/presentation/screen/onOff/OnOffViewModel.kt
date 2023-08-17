package com.dev_musashi.wakeup.presentation.screen.onOff

import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_musashi.wakeup.presentation.navigation.Screen
import com.dev_musashi.wakeup.domain.RingtoneService
import com.dev_musashi.wakeup.domain.SharedPrefRepository
import com.dev_musashi.wakeup.domain.VibratorService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class OnOffViewModel @Inject constructor(
    private val vibratorService: VibratorService,
    private val sharedPrefRepository: SharedPrefRepository,
    private val ringtoneService: RingtoneService
) : ViewModel() {
    private lateinit var countDownTimer : CountDownTimer
    var state = mutableStateOf(State())
        private set
    private val isTimerClicked get() = state.value.isTimerClicked
    private var time: Long? = null
    private var sound: Boolean? = null
    private var vibration: Boolean? = null
    private var problem: Boolean? = null
    private var problemCnt: Int? = null
    private var ringtoneUri: String? = null

    fun initSetting() {
        viewModelScope.launch {
            sharedPrefRepository.getSetting().cancellable().collectLatest {
                time = (it.time * 60000).toLong()
                sound = it.sound
                vibration = it.vibration
                problem = it.problem
                problemCnt = it.problemCnt
                ringtoneUri = it.ringtoneUri
                onTimerTextChanged(newTime = (it.time * 60000).toLong())
            }
        }
    }

    fun onTimerClicked(open: (String) -> Unit) {
        state.value = state.value.copy(isTimerClicked = !isTimerClicked)
        if (isTimerClicked) {
            startTimer(open)
        } else {
            countDownTimer.cancel()
            vibratorService.cancel()
            ringtoneService.cancel()
            resetCountDownTimer()
        }
    }

    fun onDialogOkayClicked(open: (String) -> Unit) {
        vibratorService.cancel()
        ringtoneService.cancel()
        resetCountDownTimer()
        startTimer(open)
        state.value = state.value.copy(isDialogShowed = false)
    }

    fun startTimer(open: (String) -> Unit) {
        countDownTimer = object : CountDownTimer(time!!, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                onTimerTextChanged(millisUntilFinished)
            }

            override fun onFinish() {
                if (problem == true) {
                    open(Screen.Problem.route)
                } else {
                    state.value = state.value.copy(isDialogShowed = true)
                    if (vibration == true) {
                        vibratorService.vibrating()
                    }
                    if (sound == true && ringtoneUri != null) {
                        ringtoneService.ringSelectedRingtone(ringtoneUri!!)
                    }
                }
            }
        }.start()
    }

    fun onTimerTextChanged(newTime: Long) {
        val minutes = ((newTime / 1000) / 60).toInt()
        val seconds = ((newTime / 1000) % 60).toInt()
        val timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        state.value = state.value.copy(time = timeLeftFormatted)
    }

    private fun resetCountDownTimer() {
        time?.let { onTimerTextChanged(it) }
    }

}