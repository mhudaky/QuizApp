package com.example.quizapp.quiz.swipe

import androidx.lifecycle.MutableLiveData
import com.example.quizapp.enums.Difficulty
import com.example.quizapp.utils.SharedPreferencesHelper
import java.util.logging.Logger
import kotlin.math.abs

class GameStats(private val topicName: String, private val prefsHelper: SharedPreferencesHelper) {

    var score = MutableLiveData<Int>()
    var streak = MutableLiveData<Int>()
    var difficultyLiveData = MutableLiveData<Difficulty>()
    private var currentDifficulty = Difficulty.EASY
    private val logger = Logger.getLogger(this::class.simpleName!!)

    init {
        score.value = prefsHelper.getScore(topicName)
        streak.value = prefsHelper.getStreak(topicName)
        updateDifficulty()
    }

    fun increaseStats() {
        increaseScore()
        increaseStreak()
        updateDifficulty()
    }

    fun resetStreak() {
        streak.value = 0
        prefsHelper.saveStreak(topicName, 0)
        updateDifficulty()
        logger.info("Streak reset: $this")
    }

    fun getDifficulty(): Difficulty {
        return currentDifficulty
    }

    private fun increaseScore() {
        val baseIncrement = when (currentDifficulty) {
            Difficulty.EASY -> 1
            Difficulty.MEDIUM -> 2
            Difficulty.HARD -> 3
            Difficulty.EXPERT -> 4
        }
        val mitigation = when (score.value) {
            in 0..24 -> 0
            in 25..49 -> 1
            in 50..74 -> 2
            in 75..99 -> 3
            else -> 4
        }
        val increment = abs(baseIncrement - mitigation)
        if (increment > 0) {
            val newScore = score.value!! + increment
            score.value = newScore
            prefsHelper.saveScore(topicName, newScore)
        }
        logger.info("Score increased: $this")
    }

    private fun increaseStreak() {
        streak.value = streak.value!! + 1
        prefsHelper.saveStreak(topicName, streak.value!!)
        logger.info("Streak increased: $this")
    }

    private fun updateDifficulty() {
        currentDifficulty = when (streak.value) {
            in 0..2 -> Difficulty.EASY
            in 3..5 -> Difficulty.MEDIUM
            in 6..8 -> Difficulty.HARD
            else -> Difficulty.EXPERT
        }
        logger.info("Difficulty set to: ${this.currentDifficulty}")
        difficultyLiveData.value = currentDifficulty
    }
}