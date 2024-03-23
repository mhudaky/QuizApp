package com.example.quizapp

data class TopicIdentifier(
    val name: String,
    val fileId: Int)
data class Topic(
    val name: String,
    val sections: List<Section>
) {
    init {
        val levels = sections.map { it.difficulty.name }
        if (!levels.containsAll(Difficulty.entries.map { it.name })) {
            throw IllegalArgumentException("JSON file does not contain all required sections (Easy, Medium, Hard)")
        }
    }

    fun getQuestions(difficulty: Difficulty): List<Question> {
        return sections.find { it.difficulty == difficulty }?.questions ?: emptyList()
    }
}

data class Section(
    val difficulty: Difficulty,
    val questions: List<Question>
)
data class Question(
    val question: String,
    val answers: List<String>,
    val correct: String)
