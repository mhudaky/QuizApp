package com.mhudaky.quizapp.quiz.swipe

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.AnimationDrawable
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.google.android.material.textview.MaterialTextView
import com.mhudaky.quizapp.R
import com.mhudaky.quizapp.dto.Swipe

class SwipeViewUpdater(
    activity: AppCompatActivity, viewModel: SwipeViewModel
) {

    private val statementTextView: MaterialTextView = activity.findViewById(R.id.question)
    private val questionCardView: CardView = activity.findViewById(R.id.card_view)
    private val scoreTextView: MaterialTextView = activity.findViewById(R.id.score)
    private val streakTextView: MaterialTextView = activity.findViewById(R.id.streak)
    private val difficultyTextView: MaterialTextView = activity.findViewById(R.id.difficulty)
    private val indexTextView: MaterialTextView = activity.findViewById(R.id.index)
    private val timerTextView: MaterialTextView = activity.findViewById(R.id.timer)
    private val buttonInitializer = ButtonInitializer(activity, viewModel)
    private val swipeFunctionality = SwipeFunctionality(activity, viewModel)
    private val frameLayout = activity.findViewById<FrameLayout>(R.id.background)
    private val animationDrawableRed = ContextCompat.getDrawable(activity, R.drawable.flash_red) as AnimationDrawable
    private val animationDrawableGreen = ContextCompat.getDrawable(activity, R.drawable.flash_green) as AnimationDrawable

    init {
        viewModel.swipeIterator.swipe.observe(activity) { swipe ->
            updateQuestion(swipe, viewModel.gameStats.difficultyLiveData.value!!.displayColor)
            displayColorAnimation(viewModel.answerChecker.isPreviousGuessCorrect)
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

    private fun displayColorAnimation(previousGuessCorrect: Boolean) {
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.RED, Color.TRANSPARENT)
        colorAnimation.duration = 500 // duration of the animation in milliseconds
        colorAnimation.addUpdateListener { animator ->
            frameLayout.setBackgroundColor(animator.animatedValue as Int)
        }

        val lighterGreen = ColorUtils.blendARGB(Color.GREEN, Color.WHITE, 0.5f)
        val lighterRed = ColorUtils.blendARGB(Color.RED, Color.WHITE, 0.5f)

        if (previousGuessCorrect) {
            animationDrawableGreen.stop() // Reset the animation
            frameLayout.background = animationDrawableGreen
            animationDrawableGreen.start()
            colorAnimation.setIntValues(lighterGreen, Color.TRANSPARENT)
        } else {
            animationDrawableRed.stop() // Reset the animation
            frameLayout.background = animationDrawableRed
            animationDrawableRed.start()
            colorAnimation.setIntValues(lighterRed, Color.TRANSPARENT)
        }

        colorAnimation.start()
    }

    private fun updateQuestion(swipe: Swipe, displayColor: Int) {
        statementTextView.text = swipe.question
        questionCardView.setBackgroundColor(displayColor)
    }
}