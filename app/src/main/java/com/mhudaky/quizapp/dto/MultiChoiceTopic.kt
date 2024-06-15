package com.mhudaky.quizapp.dto

import com.mhudaky.quizapp.enums.Difficulty

data class MultiChoiceTopic(
    val name: String,
    val easyQuestions: List<MultiChoice>,
    val mediumQuestions: List<MultiChoice>,
    val hardQuestions: List<MultiChoice>,
    val expertQuestions: List<MultiChoice>
) : Topic {
    fun getQuestions(currentDifficulty: Difficulty): List<MultiChoice> {
        return when (currentDifficulty) {
            Difficulty.EASY -> easyQuestions
            Difficulty.MEDIUM -> mediumQuestions
            Difficulty.HARD -> hardQuestions
            Difficulty.EXPERT -> expertQuestions
        }
    }
}