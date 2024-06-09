package com.example.quizapp.dto

data class MultiChoice(
    val question: String,
    val answers: List<String>,
    val correct: String,
    val reasoning: String
)