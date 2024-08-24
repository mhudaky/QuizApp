package com.mhudaky.quizapp.dto

data class Topic(
    val name: String,
    val filePath: String,
    val hasSubTopics: Boolean,
    var score: Int = 0
)