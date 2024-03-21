package com.example.quizapp

import MainViewModel
import SharedPreferencesHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var topicsLayout: LinearLayout
    private lateinit var viewModel: MainViewModel
    private lateinit var prefsHelper: SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        topicsLayout = findViewById(R.id.topics)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        createTopicButtons()
        addResetStatListener()
    }

    private fun createTopicButtons() {
        viewModel.getTopicNames().forEach { topicName ->
            val button = Button(this)
            button.text = topicName
            button.setOnClickListener {
                startQuestionActivity(topicName)
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

    private fun startQuestionActivity(topicName: String) {
        val intent = Intent(this, QuestionActivity::class.java)
        intent.putExtra("topic", viewModel.getTopicFileIdentifier(topicName))
        startActivity(intent)
    }
}