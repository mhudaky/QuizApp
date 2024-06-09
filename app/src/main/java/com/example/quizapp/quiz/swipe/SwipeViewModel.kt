package com.example.quizapp.quiz.swipe

import androidx.lifecycle.ViewModel
import com.example.quizapp.dto.SwipeTopic
import com.example.quizapp.utils.SharedPreferencesHelper
import java.util.logging.Logger

class SwipeViewModel(topic: SwipeTopic, prefsHelper: SharedPreferencesHelper) : ViewModel() {

    private val logger = Logger.getLogger(this::class.simpleName!!)
    val gameStats = GameStats(topic.name, prefsHelper)
    val answerChecker = AnswerChecker(gameStats)
    val swipeIterator = SwipeIterator(topic, prefsHelper)

    init {
        loadNextSwipe()
        logger.info("SwipeViewModel created: $this")
    }

    fun loadNextSwipe() {
        swipeIterator.loadSwipe(gameStats.getDifficulty())
        answerChecker.newSwipe()
    }

    fun checkAnswer(selectedAnswer: String): Boolean {
        return answerChecker.checkAnswer(selectedAnswer, swipeIterator.getSwipe())
    }
}