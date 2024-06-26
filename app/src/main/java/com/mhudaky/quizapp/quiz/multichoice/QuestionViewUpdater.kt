package com.mhudaky.quizapp.quiz.multichoice

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.mhudaky.quizapp.R
import com.mhudaky.quizapp.dto.MultiChoice
import java.util.logging.Logger

class QuestionViewUpdater(
    activity: AppCompatActivity, viewModel: QuestionViewModel
) {

    private val questionTextView: MaterialTextView = activity.findViewById(R.id.question)
    private val questionCardView: CardView = activity.findViewById(R.id.card_view)
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
        viewModel.questionIterator.question.observe(activity) { question ->
            updateQuestionView(question, viewModel.gameStats.difficultyLiveData.value!!.displayColor)
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
            if (remainingSeconds < 6) {
                timerTextView.setTextColor(Color.RED)
                timerTextView.setTypeface(null, Typeface.BOLD)
            } else {
                timerTextView.setTextColor(Color.BLACK)
                timerTextView.setTypeface(null, Typeface.NORMAL)
            }
        }
    }

    private fun updateQuestionView(question: MultiChoice, displayColor: Int) {
        logger.info("Updating question view")
        updateQuestion(question, displayColor)
        updateAnswerButtons(question)
    }

    private fun updateQuestion(question: MultiChoice, displayColor: Int) {
        questionTextView.text = question.question
        questionCardView.setBackgroundColor(displayColor)
    }

    private fun updateAnswerButtons(question: MultiChoice) {
        val shuffledAnswers = question.answers.shuffled()
        for (i in answerButtons.indices) {
            val button = answerButtons[i] as MaterialButton
            button.text = shuffledAnswers[i]
            button.isEnabled = true
            button.setBackgroundColor(Color.GRAY)
        }
    }
}