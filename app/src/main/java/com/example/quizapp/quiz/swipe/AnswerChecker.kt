package com.example.quizapp.quiz.swipe

import androidx.lifecycle.MutableLiveData
import com.example.quizapp.dto.Swipe
import com.example.quizapp.enums.SwipeDirection
import java.util.logging.Logger

class AnswerChecker(private val gameStats: GameStats) {

    val reasoning: MutableLiveData<String> = MutableLiveData()
    val timer: SwipeTimer = SwipeTimer()
    private val logger = Logger.getLogger(this::class.simpleName!!)
    init {
        timer.startTimer()
    }

    fun newSwipe() {
        reasoning.value = ""
        timer.startTimer()
    }

    fun swipe(swipeDirection: SwipeDirection, swipe: Swipe) {
        val isGuessingTrue = swipeDirection.isGuessingTrue
        val isCorrect = isGuessingTrue == swipe.answer
        if (isCorrect) {
            onRightGuess(swipe.reasoning)
        } else {
            onWrongGuess(swipe.reasoning)
        }
    }

    private fun onRightGuess(reasoning: String) {
        if(!timer.isTimeUp()) {
            gameStats.increaseStats()
        }
        this.reasoning.value = "Correct! $reasoning"
    }

    private fun onWrongGuess(reasoning: String) {
        gameStats.resetStreak()
        this.reasoning.value = "Mistake! $reasoning"
    }


}