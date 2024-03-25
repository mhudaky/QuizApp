package com.example.quizapp.question

import android.graphics.Color
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.R
import com.example.quizapp.dto.Question
import java.util.logging.Logger

class QuestionViewUpdater(
    private val activity: AppCompatActivity, viewModel: QuestionViewModel) {

    private val questionField: TextView = activity.findViewById(R.id.question)
    private val scoreTextView: TextView = activity.findViewById(R.id.score)
    private val streakTextView: TextView = activity.findViewById(R.id.streak)
    private val difficultyTextView: TextView = activity.findViewById(R.id.difficulty)
    private val indexTextView: TextView = activity.findViewById(R.id.index)
    private val reasoningTextView: TextView = activity.findViewById(R.id.reasoning)
    private val timerTextView: TextView = activity.findViewById(R.id.timer)
    private val buttonInitializer = ButtonInitializer(activity, viewModel)
    private val answerButtons = buttonInitializer.initializeButtons()

    private val logger = Logger.getLogger(this::class.simpleName!!)

    init {
        viewModel.questionIterator.question.observe(activity) { question ->
            updateQuestionView(question)
        }
        viewModel.gameStats.score.observe(activity) { score ->
            "Score: $score".also { scoreTextView.text = it }
        }
        viewModel.gameStats.streak.observe(activity) { streak ->
            "Streak: $streak".also { streakTextView.text = it }
        }
        viewModel.gameStats.difficultyLiveData.observe(activity) { difficulty ->
            "Difficulty: ${difficulty.value}".also { difficultyTextView.text = it }
        }
        viewModel.questionIterator.indexLiveData.observe(activity) { index ->
            "Index: $index".also { indexTextView.text = it }
        }
        viewModel.answerChecker.reasoning.observe(activity) { reasoning ->
            reasoning.also { reasoningTextView.text = it }
        }
        viewModel.answerChecker.timer.remainingSeconds.observe(activity) { remainingSeconds ->
            "$remainingSeconds sec".also { timerTextView.text = it }
        }
    }

    private fun updateQuestionView(question: Question) {
        logger.info("Updating question view")
        questionField.text = question.question
        val shuffledAnswers = question.answers.shuffled()
        for (i in answerButtons.indices) {
            val button = answerButtons[i]
            button.text = shuffledAnswers[i]
            button.isEnabled = true
            button.setBackgroundColor(Color.GRAY)
        }
    }
}