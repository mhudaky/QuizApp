package com.example.quizapp

import QuestionTimer
import QuestionViewModel
import QuestionViewModelFactory
import QuestionViewUpdater
import SharedPreferencesHelper
import TimerListener
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson

class QuestionActivity : AppCompatActivity(), TimerListener {

    private lateinit var viewModel: QuestionViewModel
    private lateinit var questionViewUpdater: QuestionViewUpdater
    private lateinit var timer: QuestionTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        viewModel = initViewModel()
        timer = QuestionTimer(this)
        questionViewUpdater = QuestionViewUpdater(this, viewModel, timer)
        viewModel.question.observe(this) { question ->
            questionViewUpdater.updateQuestionView(question)
            timer.startTimer()
        }
    }

    private fun initViewModel(): QuestionViewModel {
        val topicJson = intent.getStringExtra("topic")
        val topic = Gson().fromJson(topicJson, Topic::class.java)
        val prefsHelper = SharedPreferencesHelper(this)
        val factory = QuestionViewModelFactory(topic, prefsHelper)
        return ViewModelProvider(this, factory)[QuestionViewModel::class.java]
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveState()
        timer.stopTimer()
    }

    override fun onTick(secondsRemaining: Long) {
        // Handle what happens when the timer ticks
        questionViewUpdater.updateTimer(secondsRemaining)
    }

    override fun onFinish() {
        // Handle what happens when the timer finishes
        viewModel.onTimeUp()
    }
}