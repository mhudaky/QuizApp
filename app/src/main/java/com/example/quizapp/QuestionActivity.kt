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

class QuestionActivity : AppCompatActivity(), TimerListener {

    private lateinit var topicIdentifier: TopicFileLoader
    private lateinit var viewModel: QuestionViewModel
    private lateinit var questionViewUpdater: QuestionViewUpdater
    private lateinit var timer: QuestionTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topicIdentifier = TopicFileLoader(resources)
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
        val topicFileIdentifier = intent.getIntExtra("topicFileIdentifier", 0)
        val topic = topicIdentifier.loadFile(topicFileIdentifier)
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