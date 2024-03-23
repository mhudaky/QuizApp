package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        Log.i("MainActivity", "MainActivity started")
        setContentView(R.layout.activity_main)
        topicsLayout = findViewById(R.id.topics)
        val factory = MainViewModelFactory(resources)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
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
        intent.putExtra("topicFileIdentifier", viewModel.getTopicFileIdentifier(topicName))
        startActivity(intent)
    }
}