package com.dev_musashi.wakeup.presentation.screen.problem

import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_musashi.wakeup.domain.RingtoneService
import com.dev_musashi.wakeup.domain.SharedPrefRepository
import com.dev_musashi.wakeup.domain.VibratorService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.Random
import javax.inject.Inject
import kotlin.concurrent.timer


@HiltViewModel
class ProblemViewModel @Inject constructor(
    private val vibratorService: VibratorService,
    private val sharedPrefRepository: SharedPrefRepository,
    private val ringtoneService: RingtoneService
) : ViewModel() {

    private var countDownTimer: CountDownTimer? = null
    private var timerTask: Timer? = null
    var state = mutableStateOf(ProblemState())
        private set

    private val random = Random()
    private val answer get() = state.value.answer

    fun start(popUp: () -> Unit) {
        state.value = state.value.copy(init = true)
        viewModelScope.launch {
            sharedPrefRepository.getSetting().collectLatest {
                val sound = it.sound
                val vibration = it.vibration
                val problem = it.problem
                val problemCnt = it.problemCnt
                val ringtoneUri = it.ringtoneUri
                if (sound) {
                    ringtoneService.ringSelectedRingtone(ringtoneUri)
                }
                if (vibration) {
                    timerTask = timer(period = 1000) {
                        vibratorService.vibrating()
                    }
                }
                if (problem) {
                    makeProblem(problemCnt, popUp)
                }
            }
        }

    }

    private fun makeProblem(problemCnt: Int, popUp: () -> Unit) {
        var cnt = problemCnt
        val left = random.nextInt(8) + 1
        val right = random.nextInt(8) + 1
        val correctAnswer = (left * right).toString()
        state.value = state.value.copy(leftNum = left, rightNum = right)
        viewModelScope.launch {
            delay(1000)
            countDownTimer = object : CountDownTimer(5000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    state.value = state.value.copy(timer = (millisUntilFinished / 1000).toInt())
                    if (answer == correctAnswer) {
                        state.value = state.value.copy(check = "정답입니다!")
                        countDownTimer?.onFinish()
                    }
                }
                override fun onFinish() {
                    countDownTimer?.cancel()
                    cnt--
                    if (cnt > 0) {
                        if (answer != correctAnswer) {
                            state.value = state.value.copy(check = "시간 초과입니다", answer = "")
                        } else {
                            state.value = state.value.copy(answer = "", timer = 0)
                        }
                        makeProblem(cnt, popUp)
                    } else {
                        if (answer != correctAnswer) {
                            state.value = state.value.copy(check = "시간 초과")
                        }
                        viewModelScope.launch {
                            delay(1000)
                            back(popUp)
                        }
                    }

                }
            }.start()
        }

    }

    fun answerChanged(newValue: String) {
        state.value = state.value.copy(answer = newValue, check = "")
    }

    private fun back(popUp: () -> Unit) {
        vibratorService.cancel()
        timerTask?.cancel()
        ringtoneService.cancel()
        popUp()
    }

    override fun onCleared() {
        super.onCleared()
        vibratorService.cancel()
        timerTask?.cancel()
        ringtoneService.cancel()
    }

}