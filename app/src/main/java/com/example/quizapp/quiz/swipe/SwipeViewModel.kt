package com.example.quizapp.quiz.swipe

import androidx.lifecycle.ViewModel
import com.example.quizapp.dto.Topic
import com.example.quizapp.utils.SharedPreferencesHelper
import java.util.logging.Logger

class SwipeViewModel(topic: Topic, prefsHelper: SharedPreferencesHelper) : ViewModel() {

    private val logger = Logger.getLogger(this::class.simpleName!!)
    val gameStats = GameStats(topic.name, prefsHelper)
    val answerChecker = AnswerChecker(gameStats)
    val swipeIterator = SwipeIterator(topic, prefsHelper)

    init {
        loadNextQuestion()
        logger.info("QuestionViewModel created: $this")
    }

    fun loadNextQuestion() {
        swipeIterator.loadQuestion(gameStats.getDifficulty())
        answerChecker.newQuestion()
    }

    fun checkAnswer(selectedAnswer: String): Boolean {
        return answerChecker.checkAnswer(selectedAnswer, swipeIterator.getQuestion())
    }
}