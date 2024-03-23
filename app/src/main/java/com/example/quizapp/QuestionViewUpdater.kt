package com.example.quizapp

import android.graphics.Color
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuestionViewUpdater(
    private val activity: AppCompatActivity, private val viewModel: QuestionViewModel,
    private val timer: QuestionTimer
) {
    private val questionField: TextView = activity.findViewById(R.id.question)
    private val scoreTextView: TextView = activity.findViewById(R.id.score)
    private val streakTextView: TextView = activity.findViewById(R.id.streak)
    private val buttonInitializer = ButtonInitializer(activity, viewModel, timer)
    private val answerButtons = buttonInitializer.initializeButtons()

    init {
        viewModel.question.observe(activity) { question ->
            updateQuestionView(question)
        }
        viewModel.score.observe(activity) { score ->
            "Score: $score".also { scoreTextView.text = it }
        }
        viewModel.streak.observe(activity) { streak ->
            "Streak: $streak".also { streakTextView.text = it }
        }
    }

    fun updateQuestionView(question: Question) {
        questionField.text = question.question
        val shuffledAnswers = question.answers.shuffled()
        for (i in answerButtons.indices) {
            val button = answerButtons[i]
            button.text = shuffledAnswers[i]
            button.isEnabled = true
            button.setBackgroundColor(Color.GRAY)
        }
    }

    fun updateTimer(secondsRemaining: Long) {
        val timerTextView: TextView = activity.findViewById(R.id.timer)
        timerTextView.text = "$secondsRemaining sec"
    }
}