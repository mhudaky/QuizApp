package com.mhudaky.quizapp.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.mhudaky.quizapp.R
import com.mhudaky.quizapp.dto.Topic
import com.mhudaky.quizapp.dto.TopicNode
import com.mhudaky.quizapp.enums.QuestionType
import com.mhudaky.quizapp.quiz.multichoice.QuestionActivity
import com.mhudaky.quizapp.quiz.swipe.SwipeActivity
import com.mhudaky.quizapp.utils.SharedPreferencesHelperForMain
import java.util.logging.Logger.getLogger

class MainActivity : AppCompatActivity() {

    private lateinit var topicsLayout: LinearLayout
    private lateinit var prefsHelper: SharedPreferencesHelperForMain
    private lateinit var topicTreeLoader: TopicTreeLoader
    private val logger = getLogger(this::class.simpleName!!)
    private var questionType: QuestionType = QuestionType.MULTI_CHOICE
    private val buttonCreator = ButtonCreator(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.info("MainActivity started")
        prefsHelper = SharedPreferencesHelperForMain(this)
        topicTreeLoader = TopicTreeLoader(resources, prefsHelper)
        setContentView(R.layout.activity_main)
        topicsLayout = findViewById(R.id.topics)
        addResetStatListener()
        addReturnToMainListener()
        createButtonsForNode(topicTreeLoader.getRoot())
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
            val button = buttonCreator.createButton(childNode) { clickedNode ->
                if (clickedNode.topicIdentifier.hasSubTopics) {
                    createButtonsForNode(clickedNode)
                } else {
                    startQuizActivity(clickedNode.topicIdentifier)
                }
            }
            topicsLayout.addView(button)
        }
    }

    private fun addResetStatListener() {
        val resetStatsButton = findViewById<Button>(R.id.reset_stats_button)
        resetStatsButton.setOnClickListener {
            prefsHelper.resetStats()
        }
    }

    private fun addReturnToMainListener() {
        val resetStatsButton = findViewById<Button>(R.id.return_to_main_button)
        resetStatsButton.setOnClickListener {
            createButtonsForNode(topicTreeLoader.getRoot())
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