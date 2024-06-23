package com.mhudaky.quizapp.enums

enum class QuestionType(val nameInJson: String, val description: String) {
    SWIPE("swipes", "Swipe"),
    MULTI_CHOICE("multichoice", "Multi-choice question")
}