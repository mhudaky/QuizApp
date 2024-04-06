package com.example.quizapp.question.swipe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.R
import com.example.quizapp.dto.Question
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import java.util.logging.Logger

class SwipeViewUpdater(
    activity: AppCompatActivity, viewModel: SwipeViewModel
) {

    private val questionField: MaterialTextView = activity.findViewById(R.id.question)
    private val scoreTextView: MaterialTextView = activity.findViewById(R.id.score)
    private val streakTextView: MaterialTextView = activity.findViewById(R.id.streak)
    private val difficultyTextView: MaterialTextView = activity.findViewById(R.id.difficulty)
    private val indexTextView: MaterialTextView = activity.findViewById(R.id.index)
    private val reasoningTextView: MaterialTextView = activity.findViewById(R.id.reasoning)
    private val timerTextView: MaterialTextView = activity.findViewById(R.id.timer)
    private val buttonInitializer = ButtonInitializer(activity, viewModel)
    private val answerButtons = buttonInitializer.initializeButtons()
    private val logger = Logger.getLogger(this::class.simpleName!!)

    init {
        viewModel.swipeIterator.question.observe(activity) { question ->
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
        viewModel.swipeIterator.indexLiveData.observe(activity) { index ->
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
            val button = answerButtons[i] as MaterialButton
            button.text = shuffledAnswers[i]
            button.isEnabled = true
            button.setBackgroundColor(Color.GRAY)
        }
    }
}