package com.example.quizapp.question

import androidx.lifecycle.MutableLiveData
import com.example.quizapp.dto.Question
import java.util.logging.Logger

class AnswerChecker(private val gameStats: GameStats,
) {

    val reasoning: MutableLiveData<String> = MutableLiveData()
    private var guessedAlready = false
    private var timeIsUp = false
    private val logger = Logger.getLogger(this::class.simpleName!!)

    fun newQuestion() {
        if (!guessedAlready) {
            logger.info("Moved to the next question without guessing")
            gameStats.resetStreak()
        }
        reasoning.value = ""
        guessedAlready = false
        timeIsUp = false
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
        if(!guessedAlready && !timeIsUp) {
            gameStats.increaseStats()
        }
        this.reasoning.value = "Correct! $reasoning"
    }

    private fun onWrongGuess() {
        gameStats.resetStreak()
    }

    fun onTimeIsUp() {
        timeIsUp = true
        gameStats.resetStreak()
    }
}