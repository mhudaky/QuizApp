package com.example.quizapp.quiz.swipe

import androidx.lifecycle.MutableLiveData
import com.example.quizapp.dto.Swipe
import com.example.quizapp.enums.SwipeDirection
import com.example.quizapp.utils.QuestionTimer
import java.util.logging.Logger

class AnswerChecker(private val gameStats: GameStats) {


    val reasoning: MutableLiveData<String> = MutableLiveData()
    val timer: QuestionTimer = QuestionTimer()
    private val logger = Logger.getLogger(this::class.simpleName!!)
    private var guessedAlready: Boolean = false

    init {
        timer.startTimer()
    }

    fun newSwipe() {
        reasoning.value = ""
        timer.startTimer()
        guessedAlready = false
    }

    fun swipe(swipeDirection: SwipeDirection, swipe: Swipe) {
        val isGuessingTrue = swipeDirection.isGuessingTrue
        val isCorrect = isGuessingTrue == swipe.answer
        if (isCorrect) {
            onRightGuess(swipe.answer, swipe.reasoning)
        } else {
            onWrongGuess(swipe.answer, swipe.reasoning)
        }
        timer.stopTimer()
    }

    fun quitQuiz() {
        if (!guessedAlready) {
            logger.info("Quitting without answering the question")
            gameStats.resetStreak()
        }
    }

    private fun onRightGuess(rightAnswer: Boolean, reasoning: String) {
        if(!guessedAlready && !timer.isTimeUp()) {
            gameStats.increaseStats()
        } else {
            gameStats.resetStreak()
        }
        this.reasoning.value = "Correct! The statement is indeed $rightAnswer.\n$reasoning"
        guessedAlready = true
    }

    private fun onWrongGuess(rightAnswer: Boolean,reasoning: String) {
        gameStats.resetStreak()
        this.reasoning.value = "Mistake! The statement is $rightAnswer.\n$reasoning"
        guessedAlready = true
    }
}