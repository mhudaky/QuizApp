package com.example.quizapp.quiz.swipe

import androidx.lifecycle.MutableLiveData
import com.example.quizapp.dto.Question
import java.util.logging.Logger

class AnswerChecker(private val gameStats: GameStats) {

    val reasoning: MutableLiveData<String> = MutableLiveData()
    val timer: SwipeTimer = SwipeTimer()
    private var guessedAlready = false
    private val logger = Logger.getLogger(this::class.simpleName!!)

    init {
        timer.startTimer()
    }

    fun newQuestion() {
        if (!guessedAlready) {
            logger.info("Moved to the next question without guessing")
            gameStats.resetStreak()
        }
        reasoning.value = ""
        guessedAlready = false
        timer.startTimer()
    }

    fun checkAnswer(selectedAnswer: String, question: Question): Boolean {
        val isCorrect = selectedAnswer == question.correct
        if (isCorrect) {
            onRightGuess(question.reasoning)
        } else {
            onWrongGuess()
        }
        guessedAlready = true
        return isCorrect
    }

    private fun onRightGuess(reasoning: String) {
        if(!guessedAlready && !timer.isTimeUp()) {
            gameStats.increaseStats()
        }
        this.reasoning.value = "Correct! $reasoning"
    }

    private fun onWrongGuess() {
        gameStats.resetStreak()
    }
}