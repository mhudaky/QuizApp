package com.mhudaky.quizapp.enums

import android.graphics.Color
import com.mhudaky.quizapp.utils.ColorUtil.Companion.ORANGE
import com.mhudaky.quizapp.utils.ColorUtil.Companion.dark

// Define the ORANGE color

enum class Difficulty(val value: String, val displayColor: Int) {
    EASY("Easy", dark(Color.GREEN, 0.5f)),
    MEDIUM("Medium", dark(ORANGE, 0.2f)),
    HARD("Hard", dark(Color.RED, 0.5f)),
    EXPERT("Expert", dark(Color.MAGENTA, 0.5f));
}