package com.example.quizapp.quiz.swipe

import androidx.lifecycle.ViewModel
import com.example.quizapp.dto.SwipeTopic
import com.example.quizapp.enums.SwipeDirection
import com.example.quizapp.utils.SharedPreferencesHelper
import java.util.logging.Logger

class SwipeViewModel(topic: SwipeTopic, prefsHelper: SharedPreferencesHelper) : ViewModel() {

    private val logger = Logger.getLogger(this::class.simpleName!!)
    val gameStats = GameStats(topic.name, prefsHelper)
    val answerChecker = AnswerChecker(gameStats)
    val swipeIterator = SwipeIterator(topic, prefsHelper)
    var isSwipeEnabled = true

    init {
        loadNextSwipe()
        logger.info("SwipeViewModel created: $this")
    }

    fun loadNextSwipe() {
        swipeIterator.loadSwipe(gameStats.getDifficulty())
        answerChecker.newSwipe()
        isSwipeEnabled = true
    }

    fun swipe(swipeDirection: SwipeDirection) {

        logger.info("Swiped to the  $swipeDirection")
        answerChecker.swipe(swipeDirection, swipeIterator.getSwipe())
        isSwipeEnabled = false
    }
}