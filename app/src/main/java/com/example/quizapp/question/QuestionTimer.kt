package com.example.quizapp.question

import android.os.CountDownTimer

interface TimerListener {
    fun onTick(secondsRemaining: Long)
    fun onFinish()
}

class QuestionTimer(private val listener: TimerListener) {
    private var timer: CountDownTimer? = null

    fun startTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(16000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                listener.onTick(millisUntilFinished / 1000)
            }

            override fun onFinish() {
                listener.onFinish()
            }
        }
        timer?.start()
    }

    fun stopTimer() {
        timer?.cancel()
        timer = null
    }
}