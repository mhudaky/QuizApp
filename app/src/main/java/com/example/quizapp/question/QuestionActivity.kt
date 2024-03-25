package com.example.quizapp.question

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.R
import com.example.quizapp.utils.SharedPreferencesHelper
import com.example.quizapp.utils.TopicFileLoader
import java.util.logging.Logger.getLogger

class QuestionActivity : AppCompatActivity() {

    private lateinit var topicIdentifier: TopicFileLoader
    private lateinit var viewModel: QuestionViewModel
    private lateinit var questionViewUpdater: QuestionViewUpdater
    private val logger = getLogger(this::class.simpleName!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.info( "QuestionActivity started")
        topicIdentifier = TopicFileLoader(this)
        setContentView(R.layout.activity_question)
        viewModel = initViewModel()
        questionViewUpdater = QuestionViewUpdater(this, viewModel)
    }

    private fun initViewModel(): QuestionViewModel {
        val topicFileIdentifier = intent.getStringExtra("topicFilePath") ?: ""
        val topic = topicIdentifier.loadFile(topicFileIdentifier)
        logger.info( "Loaded topic: $topic")
        val prefsHelper = SharedPreferencesHelper(this)
        val factory = QuestionViewModelFactory(topic, prefsHelper)
        return ViewModelProvider(this, factory)[QuestionViewModel::class.java]
    }
}