package com.dev_musashi.wakeup.presentation.onOff

import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_musashi.wakeup.common.Screen
import com.dev_musashi.wakeup.domain.RingtoneService
import com.dev_musashi.wakeup.domain.SharedPrefRepository
import com.dev_musashi.wakeup.domain.VibratorService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.timer

@HiltViewModel
class OnOffViewModel @Inject constructor(
    private val vibratorService: VibratorService,
    private val sharedPrefRepository: SharedPrefRepository,
    private val ringtoneService: RingtoneService
) : ViewModel() {
    private lateinit var countDownTimer: CountDownTimer
    var state = mutableStateOf(State())
        private set
    private var timerTask: Timer? = null

    private val buttonState get() = state.value.buttonState
    private val buttonText get() = state.value.buttonText

    private var startTime: Long? = null
    private var leftTime: Long? = null
    private var sound: Boolean? = null
    private var vibration: Boolean? = null
    private var problem: Boolean? = null
    private var problemCnt: Int? = null
    private var ringtoneUri: String? = null
    var timerRunning = false

    fun initSetting() {
        viewModelScope.launch {
            sharedPrefRepository.getSetting().collectLatest {
                startTime = (it.time * 60000).toLong()
                leftTime = (it.time * 60000).toLong()
                sound = it.sound
                vibration = it.vibration
                problem = it.problem
                problemCnt = it.problemCnt
                ringtoneUri = it.ringtoneUri
                updateCountDownText()
            }
        }
    }

    fun buttonClick(open: (String) -> Unit) {
        state.value = state.value.copy(
            buttonText = if (buttonText == "On") "Off" else "On",
            buttonState = !buttonState
        )

        if (buttonState) {
            startCountdownTimer(open)
        } else {
            countDownTimer.cancel()
            resetCountDownTimer()
            vibratorService.cancel()
            timerTask?.cancel()
        }


    }

    fun onConfirmClicked(open: (String) -> Unit) {
        vibratorService.cancel()
        timerTask?.cancel()
        ringtoneService.onDestroy()
        resetCountDownTimer()
        startCountdownTimer(open)
        state.value = state.value.copy(showDialog = false)
    }

    fun startCountdownTimer(open: (String) -> Unit) {
        countDownTimer = object : CountDownTimer(leftTime!!, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                leftTime = millisUntilFinished
                updateCountDownText()
            }
            override fun onFinish() {
                timerRunning = false
                if (problem == true) {
                    open(Screen.Problem.route)
                } else {
                    state.value = state.value.copy(showDialog = true)
                    if (vibration == true) {
                        timerTask = timer(period = leftTime!!) {
                            vibratorService.vibrating()
                        }
                    }
                    if (sound == true) {
                        ringtoneService.getRingtoneList()
                    }
                }
            }
        }.start()
        timerRunning = true

    }

    fun updateCountDownText() {
        val minutes = ((leftTime!! / 1000) / 60).toInt()
        val seconds = ((leftTime!! / 1000) % 60).toInt()
        val timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        state.value = state.value.copy(time = timeLeftFormatted)
    }

    private fun resetCountDownTimer() {
        leftTime = startTime
        updateCountDownText()
    }
}