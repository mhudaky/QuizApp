package com.example.quizapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.util.logging.Logger.getLogger

class QuestionActivity : AppCompatActivity(), TimerListener {

    private lateinit var topicIdentifier: TopicFileLoader
    private lateinit var viewModel: QuestionViewModel
    private lateinit var questionViewUpdater: QuestionViewUpdater
    private lateinit var timer: QuestionTimer
    private val logger = getLogger(this::class.simpleName!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.info( "QuestionActivity started")
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
        logger.info( "Loaded topic: $topic")
        logger.info( "Easy: ${topic.getQuestions(Difficulty.EASY)}")
        val prefsHelper = SharedPreferencesHelper(this)
        val factory = QuestionViewModelFactory(topic, prefsHelper)
        return ViewModelProvider(this, factory)[QuestionViewModel::class.java]
    }

    override fun onStop() {
        super.onStop()
        timer.stopTimer()
    }

    override fun onTick(secondsRemaining: Long) {
        questionViewUpdater.updateTimer(secondsRemaining)
    }

    override fun onFinish() {
        viewModel.onTimeIsUp()
    }
}