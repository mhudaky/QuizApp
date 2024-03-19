package com.example.quizapp

data class Topic(val name: String, val questions: List<Question>)
data class Question(val question: String, val answers: List<String>, val correct: String)
data class QuestionBank(val topics: List<Topic>)
