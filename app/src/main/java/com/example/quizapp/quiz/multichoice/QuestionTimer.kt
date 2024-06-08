package com.example.quizapp.quiz.multichoice

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData

class QuestionTimer() {

    private var timer: CountDownTimer? = null
    val remainingSeconds: MutableLiveData<Int> = MutableLiveData()

    fun startTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(16000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingSeconds.value = millisUntilFinished.toInt() / 1000
            }

            override fun onFinish() {
            }
        }
        timer?.start()
    }

    fun isTimeUp(): Boolean {
        return remainingSeconds.value == 0
    }
}