package com.example.quizapp.quiz.swipe

import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.R
import com.google.android.material.textview.MaterialTextView
import java.util.logging.Logger

class SwipeViewUpdater(
    activity: AppCompatActivity, viewModel: SwipeViewModel
) {

    private val swipeField: MaterialTextView = activity.findViewById(R.id.swipe)
    private val scoreTextView: MaterialTextView = activity.findViewById(R.id.score)
    private val streakTextView: MaterialTextView = activity.findViewById(R.id.streak)
    private val difficultyTextView: MaterialTextView = activity.findViewById(R.id.difficulty)
    private val indexTextView: MaterialTextView = activity.findViewById(R.id.index)
    private val reasoningTextView: MaterialTextView = activity.findViewById(R.id.reasoning)
    private val timerTextView: MaterialTextView = activity.findViewById(R.id.timer)
    private val logger = Logger.getLogger(this::class.simpleName!!)

    init {
        viewModel.swipeIterator.swipe.observe(activity) { swipe ->
            swipe.also { reasoningTextView.text = it.swipe }
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
}