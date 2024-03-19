package com.example.quizapp

import MainViewModel
import SharedPreferencesHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var topicsLayout: LinearLayout
    private lateinit var viewModel: MainViewModel
    private lateinit var prefsHelper: SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topicsLayout = findViewById(R.id.topics)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Load your topics and questions here
        val inputStream = resources.openRawResource(R.raw.questions)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        viewModel.loadQuestions(jsonString)

        loadTopics()
    }

    private fun loadTopics() {
        viewModel.getTopics().forEach { topic ->
            val button = Button(this)
            button.text = topic.name
            button.setOnClickListener {
                startQuestionActivity(topic)
            }
            topicsLayout.addView(button)
        }
    }

    private fun resetStats() {
        prefsHelper = SharedPreferencesHelper(this)

        val resetStatsButton = findViewById<Button>(R.id.reset_stats_button)
        resetStatsButton.setOnClickListener {
            prefsHelper.resetStats()
        }
    }

    private fun startQuestionActivity(topic: Topic) {
        val intent = Intent(this, QuestionActivity::class.java)
        intent.putExtra("topic", Gson().toJson(topic))
        intent.putExtra("score", 0)
        intent.putExtra("streak", 0)
        startActivity(intent)
    }
}