package com.example.quizapp.question

import androidx.lifecycle.ViewModel
import com.example.quizapp.dto.Topic
import com.example.quizapp.utils.SharedPreferencesHelper
import java.util.logging.Logger

class QuestionViewModel(topic: Topic, prefsHelper: SharedPreferencesHelper) : ViewModel() {

    private val logger = Logger.getLogger(this::class.simpleName!!)
    val gameStats = GameStats(topic.name, prefsHelper)
    val answerChecker = AnswerChecker(gameStats)
    val questionIterator = QuestionIterator(topic, prefsHelper)

    init {
        loadNextQuestion()
        logger.info("QuestionViewModel created: $this")
    }

    fun loadNextQuestion() {
        questionIterator.loadQuestion(gameStats.getDifficulty())
        answerChecker.newQuestion()
    }

    fun checkAnswer(selectedAnswer: String): Boolean {
        return answerChecker.checkAnswer(selectedAnswer, questionIterator.getQuestion())
    }
}