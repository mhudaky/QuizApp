package com.example.quizapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class QuestionActivity : AppCompatActivity(), TimerListener {

    private lateinit var topicIdentifier: TopicFileLoader
    private lateinit var viewModel: QuestionViewModel
    private lateinit var questionViewUpdater: QuestionViewUpdater
    private lateinit var timer: QuestionTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("QuestionActivity", "QuestionActivity started")
        topicIdentifier = TopicFileLoader(this)
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
        val topicFileIdentifier = intent.getStringExtra("topicFileIdentifier") ?: ""
        val topic = topicIdentifier.loadFile(topicFileIdentifier)
        Log.i("QuestionActivity", "Loaded topic: $topic")
        Log.i("QuestionActivity", "Easy: ${topic.getQuestions(Difficulty.EASY)}")
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