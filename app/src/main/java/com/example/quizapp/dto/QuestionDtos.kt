package com.example.quizapp.dto

data class TopicIdentifier(
    val name: String,
    val filePath: String,
    val hasSubTopics: Boolean
)

data class Topic(
    val name: String,
    val easyQuestions: List<Question>,
    val mediumQuestions: List<Question>,
    val hardQuestions: List<Question>
) {
    fun getQuestions(currentDifficulty: Difficulty): List<Question> {
        return when (currentDifficulty) {
            Difficulty.EASY -> easyQuestions
            Difficulty.MEDIUM -> mediumQuestions
            Difficulty.HARD -> hardQuestions
        }
    }
}

data class Question(
    val question: String,
    val answers: List<String>,
    val correct: String,
    val reasoning: String
)
