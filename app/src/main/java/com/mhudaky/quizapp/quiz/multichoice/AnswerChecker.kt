package com.mhudaky.quizapp.quiz.multichoice

import androidx.lifecycle.MutableLiveData
import com.mhudaky.quizapp.dto.MultiChoice
import com.mhudaky.quizapp.utils.QuestionTimer
import java.util.logging.Logger

class AnswerChecker(private val gameStats: GameStats) {

    val reasoning: MutableLiveData<String> = MutableLiveData()
    val timer: QuestionTimer = QuestionTimer()
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

    fun checkAnswer(selectedAnswer: String, question: MultiChoice): Boolean {
        val isCorrect = selectedAnswer == question.correct
        if (isCorrect) {
            onRightGuess(question.reasoning)
        } else {
            onWrongGuess()
        }
        guessedAlready = true
        return isCorrect
    }

    fun quitQuiz() {
        if (!guessedAlready) {
            logger.info("Quitting without answering the question")
            gameStats.resetStreak()
        }
    }

    private fun onRightGuess(reasoning: String) {
        if(!guessedAlready && !timer.isTimeUp()) {
            gameStats.increaseStats()
        } else {
            gameStats.resetStreak()
        }
        timer.stopTimer()
        this.reasoning.value = "Correct! $reasoning"
    }

    private fun onWrongGuess() {
        gameStats.resetStreak()
    }
}