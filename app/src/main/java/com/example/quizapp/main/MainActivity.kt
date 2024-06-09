package com.example.quizapp.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.R
import com.example.quizapp.dto.TopicIdentifier
import com.example.quizapp.enums.QuestionType
import com.example.quizapp.quiz.multichoice.QuestionActivity
import com.example.quizapp.quiz.swipe.SwipeActivity
import com.example.quizapp.utils.SharedPreferencesHelper
import java.util.logging.Logger.getLogger

class MainActivity : AppCompatActivity() {

    private lateinit var topicsLayout: LinearLayout
    private lateinit var viewModel: MainViewModel
    private lateinit var prefsHelper: SharedPreferencesHelper
    private val logger = getLogger(this::class.simpleName!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.info("MainActivity started")
        setContentView(R.layout.activity_main)
        topicsLayout = findViewById(R.id.topics)
        addResetStatListener()
        addReturnToMainListener()
        val factory = MainViewModelFactory(resources)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        createTopicButtons()
    }

    private fun createTopicButtons() {
        topicsLayout.removeAllViews()
        val topicIdentifiers = viewModel.getTopicIdentifiers()
        logger.info("Creating topic buttons: $topicIdentifiers")
        topicIdentifiers.forEach { topicIdentifier ->
            val button = Button(this)
            button.text = topicIdentifier.name
            button.setOnClickListener {
                viewModel.choseTopic(topicIdentifier)
                if (topicIdentifier.hasSubTopics) {
                    createTopicButtons()
                } else {
                    startQuizActivity(topicIdentifier)
                }
            }
            topicsLayout.addView(button)
        }
    }

    private fun addResetStatListener() {
        prefsHelper = SharedPreferencesHelper(this, "")
        val resetStatsButton = findViewById<Button>(R.id.reset_stats_button)
        resetStatsButton.setOnClickListener {
            prefsHelper.resetStats()
        }
    }

    private fun addReturnToMainListener() {
        val resetStatsButton = findViewById<Button>(R.id.return_to_main_button)
        resetStatsButton.setOnClickListener {
            viewModel.reset()
            createTopicButtons()
        }
    }

    private fun startQuizActivity(topicIdentifier: TopicIdentifier) {
        val intent = when (topicIdentifier.questionType) {
            QuestionType.MULTI_CHOICE -> Intent(this, QuestionActivity::class.java)
            QuestionType.SWIPE -> Intent(this, SwipeActivity::class.java)
        }
        intent.putExtra("topicName", topicIdentifier.name)
        intent.putExtra("topicFilePath", topicIdentifier.filePath)
        startActivity(intent)
    }
}