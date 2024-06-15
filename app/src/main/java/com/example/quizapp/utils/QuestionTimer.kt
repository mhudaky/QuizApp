package com.example.quizapp.utils

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData

class QuestionTimer() {

    private var timer: CountDownTimer? = null
    val remainingSeconds: MutableLiveData<Int> = MutableLiveData()

    fun startTimer(seconds: Long = 16) {
        timer?.cancel()
        val milliseconds: Long = 1000 * seconds
        timer = object : CountDownTimer(milliseconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingSeconds.value = millisUntilFinished.toInt() / 1000
            }

            override fun onFinish() {
            }
        }
        timer?.start()
    }

    fun stopTimer() {
        timer?.cancel()
    }

    fun isTimeUp(): Boolean {
        return remainingSeconds.value == 0
    }
}