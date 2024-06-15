package com.mhudaky.quizapp.dto

import com.mhudaky.quizapp.enums.QuestionType

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