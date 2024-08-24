package com.mhudaky.quizapp.main

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mhudaky.quizapp.R
import com.mhudaky.quizapp.dto.Topic
import com.mhudaky.quizapp.dto.TopicNode
import com.mhudaky.quizapp.enums.QuestionType
import com.mhudaky.quizapp.quiz.multichoice.QuestionActivity
import com.mhudaky.quizapp.quiz.swipe.SwipeActivity
import com.mhudaky.quizapp.utils.ColorUtil.Companion.getColorFromInt
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
        createButtonsForNode(viewModel.getRoot())
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

    private fun createButtonsForNode(node: TopicNode?) {
        topicsLayout.removeAllViews()
        node?.children?.forEach { childNode ->
            val button = Button(this)
            button.text = childNode.topicIdentifier.name.uppercase()
            setBackgroundColorOnButton(button, childNode)
            button.setOnClickListener {
                if (childNode.topicIdentifier.hasSubTopics) {
                    createButtonsForNode(childNode)
                } else {
                    startQuizActivity(childNode.topicIdentifier)
                }
            }
            topicsLayout.addView(button)
        }
    }

    private fun setBackgroundColorOnButton(button: Button, node: TopicNode) {
        val color = calculateColor(node)
        setButtonColor(button, color)
    }

    private fun calculateColor(node: TopicNode): Int {
        val score = calculateScore(node)
        return getColorFromInt(score)
    }

    private fun calculateScore(node: TopicNode): Int {
        if (node.topicIdentifier.hasSubTopics) {
            val subTopicScores = node.children.map { calculateScore(it) }
            return subTopicScores.average().toInt()
        }
        return prefsHelper.getScoreForTopic(node.topicIdentifier.name)
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
            createButtonsForNode(viewModel.getRoot())
        }
    }

    private fun startQuizActivity(topicIdentifier: Topic) {
        val intent = when (questionType) {
            QuestionType.MULTI_CHOICE -> Intent(this, QuestionActivity::class.java)
            QuestionType.SWIPE -> Intent(this, SwipeActivity::class.java)
        }
        intent.putExtra("topicName", topicIdentifier.name)
        intent.putExtra("topicFilePath", topicIdentifier.filePath)
        startActivity(intent)
    }
}