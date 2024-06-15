package com.mhudaky.quizapp.dto

import com.mhudaky.quizapp.enums.Difficulty

data class TopicDto(
    val topicName: String,
    val difficulty: Difficulty
)