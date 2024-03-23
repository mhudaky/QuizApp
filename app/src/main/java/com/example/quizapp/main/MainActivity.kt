package com.example.quizapp.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.R
import com.example.quizapp.dto.TopicIdentifier
import com.example.quizapp.question.QuestionActivity
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
        val factory = MainViewModelFactory(resources)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        createTopicButtons()
        addResetStatListener()
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
                    startQuestionActivity(topicIdentifier)
                }
            }
            topicsLayout.addView(button)
        }
    }

    private fun addResetStatListener() {
        prefsHelper = SharedPreferencesHelper(this)
        val resetStatsButton = findViewById<Button>(R.id.reset_stats_button)
        resetStatsButton.setOnClickListener {
            prefsHelper.resetStats()
        }
    }

    private fun startQuestionActivity(topicIdentifier: TopicIdentifier) {
        val intent = Intent(this, QuestionActivity::class.java)
        intent.putExtra("topicName", topicIdentifier.name)
        intent.putExtra("topicFilePath", topicIdentifier.filePath)
        startActivity(intent)
    }
}