package com.mhudaky.quizapp.dto

import com.mhudaky.quizapp.enums.Difficulty

data class MultiChoiceTopic(
    var name: String,
    val easy: List<MultiChoice>,
    val medium: List<MultiChoice>,
    val hard: List<MultiChoice>,
    val expert: List<MultiChoice>
) : Topic {
    fun getQuestions(currentDifficulty: Difficulty): List<MultiChoice> {
        return when (currentDifficulty) {
            Difficulty.EASY -> easy
            Difficulty.MEDIUM -> medium
            Difficulty.HARD -> hard
            Difficulty.EXPERT -> expert
        }
    }
}