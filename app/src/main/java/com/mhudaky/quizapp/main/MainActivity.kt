package com.mhudaky.quizapp.main

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mhudaky.quizapp.R
import com.mhudaky.quizapp.dto.TopicIdentifier
import com.mhudaky.quizapp.enums.QuestionType
import com.mhudaky.quizapp.quiz.multichoice.QuestionActivity
import com.mhudaky.quizapp.quiz.swipe.SwipeActivity
import com.mhudaky.quizapp.utils.ColorUtil.Companion.getColorFromInt
import com.mhudaky.quizapp.utils.ColorUtil.Companion.lightGrey
import com.mhudaky.quizapp.utils.SharedPreferencesHelperForMain
import java.util.logging.Logger.getLogger

class MainActivity : AppCompatActivity() {

    private lateinit var topicsLayout: LinearLayout
    private lateinit var viewModel: MainViewModel
    private lateinit var prefsHelper: SharedPreferencesHelperForMain
    private val logger = getLogger(this::class.simpleName!!)
    private var questionType: QuestionType = QuestionType.MULTI_CHOICE

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
        addSwitchQuestionTypeListener()
    }

    private fun addSwitchQuestionTypeListener() {
        val switchQuestionTypeButton = findViewById<Button>(R.id.switch_question_type_button)
        switchQuestionTypeButton.text = questionType.description
        switchQuestionTypeButton.setOnClickListener {
            questionType = if (questionType == QuestionType.MULTI_CHOICE) QuestionType.SWIPE else QuestionType.MULTI_CHOICE
            switchQuestionTypeButton.text = questionType.description
        }
    }

    private fun createTopicButtons() {
        topicsLayout.removeAllViews()
        val topicIdentifiers = viewModel.getTopicIdentifiers()
        logger.info("Creating topic buttons: $topicIdentifiers")
        topicIdentifiers.forEach { topicIdentifier ->
            val button = Button(this)
            button.text = topicIdentifier.name.uppercase()
            setBackgroundColorOnButton(button, topicIdentifier)
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

    private fun setBackgroundColorOnButton(button: Button, topicIdentifier: TopicIdentifier) {
        val color = calculateColor(topicIdentifier)
        setButtonColor(button, color)
    }

    private fun calculateColor(topicIdentifier: TopicIdentifier): Int {
        if (topicIdentifier.hasSubTopics) {
            return lightGrey
        }
        val score = prefsHelper.getScoreForTopic(topicIdentifier.name)
        return getColorFromInt(score)
    }

    private fun setButtonColor(button: Button, color: Int) {
        button.setBackgroundTintList(ColorStateList.valueOf(color))
    }

    private fun addResetStatListener() {
        prefsHelper = SharedPreferencesHelperForMain(this)
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
        val intent = when (questionType) {
            QuestionType.MULTI_CHOICE -> Intent(this, QuestionActivity::class.java)
            QuestionType.SWIPE -> Intent(this, SwipeActivity::class.java)
        }
        intent.putExtra("topicName", topicIdentifier.name)
        intent.putExtra("topicFilePath", topicIdentifier.filePath)
        startActivity(intent)
    }
}