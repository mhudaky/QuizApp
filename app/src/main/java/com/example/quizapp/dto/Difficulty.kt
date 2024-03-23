package com.example.quizapp.dto

enum class Difficulty(val value: String) {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    companion object {
        fun fromString(value: String): Difficulty {
            return values().first { it.value == value }
        }
    }
}