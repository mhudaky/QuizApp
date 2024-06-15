package com.example.quizapp.quiz.swipe

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.R
import com.example.quizapp.dto.SwipeTopic
import com.example.quizapp.enums.QuestionType
import com.example.quizapp.enums.SwipeDirection
import com.example.quizapp.utils.SharedPreferencesHelper
import com.example.quizapp.utils.TopicFileLoader
import java.util.logging.Logger.getLogger

class SwipeActivity : AppCompatActivity() {

    private lateinit var topicIdentifier: TopicFileLoader
    private lateinit var viewModel: SwipeViewModel
    private lateinit var swipeViewUpdater: SwipeViewUpdater
    private val logger = getLogger(this::class.simpleName!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.info( "SwipeActivity started")
        topicIdentifier = TopicFileLoader(this)
        setContentView(R.layout.activity_swipe)
        viewModel = initViewModel()
        swipeViewUpdater = SwipeViewUpdater(this, viewModel)
        setUpGestureDetector()
    }

@SuppressLint("ClickableViewAccessibility")
private fun setUpGestureDetector() {
    val gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            if (e1 != null) {
                val swipeDistanceToLeft = e1.x - e2.x
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
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }
    })
    val cardView = findViewById<CardView>(R.id.card_view)
    cardView.setOnTouchListener { _, event ->
        gestureDetector.onTouchEvent(event)
        return@setOnTouchListener true // Return true to continue receiving touch events
    }
}

    private fun initViewModel(): SwipeViewModel {
        val topicFileIdentifier = intent.getStringExtra("topicFilePath") ?: ""
        val topic = topicIdentifier.loadFile(topicFileIdentifier, SwipeTopic::class.java)
        logger.info( "Loaded topic: $topic")
        val prefsHelper = SharedPreferencesHelper(this, QuestionType.SWIPE.name)
        val factory = SwipeViewModelFactory(topic, prefsHelper)
        return ViewModelProvider(this, factory)[SwipeViewModel::class.java]
    }
}