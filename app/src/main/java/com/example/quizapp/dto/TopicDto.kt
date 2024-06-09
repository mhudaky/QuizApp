package com.example.quizapp.dto

import com.example.quizapp.enums.Difficulty

data class TopicDto(
    val topicName: String,
    val difficulty: Difficulty
)