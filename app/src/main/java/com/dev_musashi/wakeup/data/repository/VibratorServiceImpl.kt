package com.dev_musashi.wakeup.data.repository

import android.content.Context
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.dev_musashi.wakeup.domain.VibratorService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class VibratorServiceImpl @Inject constructor(
    @ApplicationContext val context: Context
) : VibratorService {

    @Suppress("DEPRECATION")
    override fun vibrating() {
        val pattern = longArrayOf(100, 200, 100, 200, 100, 200)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context.applicationContext.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vibrationEffect = VibrationEffect.createWaveform(pattern, -1)
            val combinedVibration = CombinedVibration.createParallel(vibrationEffect)
            vibratorManager.vibrate(combinedVibration)
        } else  {
            val vibrator = context.applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                vibrator.vibrate(pattern,-1)
            }
        }
    }

    @Suppress("DEPRECATION")
    override fun cancel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context.applicationContext.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.cancel()
        } else  {
            val vibrator = context.applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.cancel()
        }
    }


}