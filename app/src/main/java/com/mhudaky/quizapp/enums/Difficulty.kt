package com.mhudaky.quizapp.enums

import android.graphics.Color.GREEN
import android.graphics.Color.MAGENTA
import android.graphics.Color.RED
import android.graphics.Color.YELLOW

enum class Difficulty(val value: String, val displayColor: Int) {
    EASY("Easy", GREEN),
    MEDIUM("Medium", YELLOW),
    HARD("Hard", RED),
    EXPERT("Expert", MAGENTA);
}