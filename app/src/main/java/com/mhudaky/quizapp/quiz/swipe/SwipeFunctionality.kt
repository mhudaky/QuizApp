package com.mhudaky.quizapp.quiz.swipe

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.mhudaky.quizapp.R
import com.mhudaky.quizapp.enums.SwipeDirection
import java.util.logging.Logger

class SwipeFunctionality(private val activity: AppCompatActivity, private val viewModel: SwipeViewModel) {

    private val logger = Logger.getLogger(this::class.simpleName!!)
    private var initialX = 0f
    private var originalX = 0f

    @SuppressLint("ClickableViewAccessibility")
    fun setUpGestureDetector() {
        val gestureDetector = GestureDetector(activity, object : GestureDetector.SimpleOnGestureListener(){})
        val cardView = activity.findViewById<CardView>(R.id.card_view)
        cardView.setOnTouchListener { v, event ->
            if (viewModel.isSwipeEnabled) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = event.rawX
                        originalX = v.x
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val offsetX = event.rawX - initialX
                        v.animate()
                            .x(v.x + offsetX)
                            .setDuration(0)
                            .start()
                        initialX = event.rawX
                    }
                    MotionEvent.ACTION_UP -> {
                        val swipeDistanceToLeft = originalX - v.x
                        val minSwipeDistance = 100
                        if (swipeDistanceToLeft > minSwipeDistance) {
                            logger.info("Swiped to the LEFT")
                            viewModel.swipe(SwipeDirection.LEFT)
                        } else if (-swipeDistanceToLeft > minSwipeDistance) {
                            logger.info("Swiped to the RIGHT")
                            viewModel.swipe(SwipeDirection.RIGHT)
                        } else {
                            logger.info("Swipe was too short")
                        }
                        v.animate()
                            .x(originalX)
                            .setDuration(200)
                            .start()
                    }
                }
                gestureDetector.onTouchEvent(event)
            }
            return@setOnTouchListener true
        }
    }
}