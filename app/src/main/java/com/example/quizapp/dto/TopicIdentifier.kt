package com.example.quizapp.dto

import com.example.quizapp.enums.QuestionType

data class TopicIdentifier(
    val name: String,
    val filePath: String,
    val hasSubTopics: Boolean
) {
    val questionType: QuestionType = when {
        filePath.startsWith("quiz/swipe") -> QuestionType.SWIPE
        filePath.startsWith("quiz/question") -> QuestionType.MULTI_CHOICE
        else -> throw IllegalArgumentException("Invalid filePath: $filePath")
    }
}