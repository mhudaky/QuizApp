package com.mhudaky.quizapp.dto

import com.mhudaky.quizapp.enums.Difficulty

data class SwipeTopic(
    val name: String,
    val easySwipes: List<Swipe>,
    val mediumSwipes: List<Swipe>,
    val hardSwipes: List<Swipe>,
    val expertSwipes: List<Swipe>
) : Topic {
    fun getSwipes(currentDifficulty: Difficulty): List<Swipe> {
        return when (currentDifficulty) {
            Difficulty.EASY -> easySwipes
            Difficulty.MEDIUM -> mediumSwipes
            Difficulty.HARD -> hardSwipes
            Difficulty.EXPERT -> expertSwipes
        }
    }
}