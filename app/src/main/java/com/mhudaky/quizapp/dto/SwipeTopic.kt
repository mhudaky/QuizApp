package com.mhudaky.quizapp.dto

import com.mhudaky.quizapp.enums.Difficulty

data class SwipeTopic(
    var name: String,
    val easy: List<Swipe>,
    val medium: List<Swipe>,
    val hard: List<Swipe>,
    val expert: List<Swipe>
) : Topic {
    fun getSwipes(currentDifficulty: Difficulty): List<Swipe> {
        return when (currentDifficulty) {
            Difficulty.EASY -> easy
            Difficulty.MEDIUM -> medium
            Difficulty.HARD -> hard
            Difficulty.EXPERT -> expert
        }
    }
}