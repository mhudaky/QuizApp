package com.example.quizapp.quiz.swipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.R
import com.example.quizapp.dto.SwipeTopic
import com.example.quizapp.enums.QuestionType
import com.example.quizapp.utils.SharedPreferencesHelper
import com.example.quizapp.utils.TopicFileLoader
import java.util.logging.Logger.getLogger

class SwipeActivity : AppCompatActivity() {

    private lateinit var topicIdentifier: TopicFileLoader
    private lateinit var viewModel: SwipeViewModel
    private lateinit var swipeViewUpdater: SwipeViewUpdater
    private val logger = getLogger(this::class.simpleName!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.info( "SwipeActivity started")
        topicIdentifier = TopicFileLoader(this)
        setContentView(R.layout.activity_swipe)
        viewModel = initViewModel()
        swipeViewUpdater = SwipeViewUpdater(this, viewModel)
    }

    private fun initViewModel(): SwipeViewModel {
        val topicFileIdentifier = intent.getStringExtra("topicFilePath") ?: ""
        val topic = topicIdentifier.loadFile(topicFileIdentifier, SwipeTopic::class.java)
        logger.info( "Loaded topic: $topic")
        val prefsHelper = SharedPreferencesHelper(this, QuestionType.SWIPE.name)
        val factory = SwipeViewModelFactory(topic, prefsHelper)
        return ViewModelProvider(this, factory)[SwipeViewModel::class.java]
    }
}