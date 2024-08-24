package com.mhudaky.quizapp.enums

import android.graphics.Color
import androidx.core.graphics.ColorUtils

enum class Difficulty(val value: String, val displayColor: Int) {
    EASY("Easy", ColorUtils.blendARGB(Color.GREEN, Color.BLACK, 0.5f)),
    MEDIUM("Medium", ColorUtils.blendARGB(Color.YELLOW, Color.BLACK, 0.5f)),
    HARD("Hard", ColorUtils.blendARGB(Color.RED, Color.BLACK, 0.5f)),
    EXPERT("Expert", ColorUtils.blendARGB(Color.MAGENTA, Color.BLACK, 0.5f));
}