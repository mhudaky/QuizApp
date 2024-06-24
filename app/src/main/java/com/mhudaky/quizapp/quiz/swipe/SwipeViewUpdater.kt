package com.mhudaky.quizapp.quiz.swipe

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textview.MaterialTextView
import com.mhudaky.quizapp.R

class SwipeViewUpdater(
    activity: AppCompatActivity, viewModel: SwipeViewModel
) {

    private val statementTextView: MaterialTextView = activity.findViewById(R.id.question)
    private val scoreTextView: MaterialTextView = activity.findViewById(R.id.score)
    private val streakTextView: MaterialTextView = activity.findViewById(R.id.streak)
    private val difficultyTextView: MaterialTextView = activity.findViewById(R.id.difficulty)
    private val indexTextView: MaterialTextView = activity.findViewById(R.id.index)
    private val reasoningTextView: MaterialTextView = activity.findViewById(R.id.reasoning)
    private val timerTextView: MaterialTextView = activity.findViewById(R.id.timer)
    private val buttonInitializer = ButtonInitializer(activity, viewModel)
    private val swipeFunctionality = SwipeFunctionality(activity, viewModel)

    init {
        viewModel.swipeIterator.swipe.observe(activity) { swipe ->
            swipe.also { statementTextView.text = it.question }
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
            if (remainingSeconds < 6) {
                timerTextView.setTextColor(Color.RED)
                timerTextView.setTypeface(null, Typeface.BOLD)
            } else {
                timerTextView.setTextColor(Color.BLACK)
                timerTextView.setTypeface(null, Typeface.NORMAL)
            }
        }
        buttonInitializer.initializeButtons()
        swipeFunctionality.setUpGestureDetector()
    }
}