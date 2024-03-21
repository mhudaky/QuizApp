package com.example.quizapp

data class TopicIdentifier(val name: String, val fileId: Int)
data class Topic(val name: String, val questions: List<Question>)
data class Question(val question: String, val answers: List<String>, val correct: String)
