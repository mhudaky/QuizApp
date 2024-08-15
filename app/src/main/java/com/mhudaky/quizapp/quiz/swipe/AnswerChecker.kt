package com.mhudaky.quizapp.quiz.swipe

import com.mhudaky.quizapp.dto.Swipe
import com.mhudaky.quizapp.enums.SwipeDirection
import com.mhudaky.quizapp.utils.QuestionTimer

class AnswerChecker(private val gameStats: GameStats) {


    var isPreviousGuessCorrect: Boolean = false
    val timer: QuestionTimer = QuestionTimer()

    init {
        timer.startTimer()
    }

    fun newSwipe() {
        timer.startTimer()
    }

    fun swipe(swipeDirection: SwipeDirection, swipe: Swipe) {
        val isGuessingTrue = swipeDirection.isGuessingTrue
        val isCorrect = isGuessingTrue == swipe.answer
        if (isCorrect) {
            onRightGuess()
        } else {
            onWrongGuess()
        }
        timer.stopTimer()
    }

    fun quitQuiz() {
        gameStats.resetStreak()
    }

    private fun onRightGuess() {
        if(!timer.isTimeUp()) {
            gameStats.increaseStats()
        } else {
            gameStats.resetStreak()
        }
        this.isPreviousGuessCorrect = true
    }

    private fun onWrongGuess() {
        gameStats.resetStreak()
        this.isPreviousGuessCorrect = false
    }
}