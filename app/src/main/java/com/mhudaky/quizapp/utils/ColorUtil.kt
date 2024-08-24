package com.mhudaky.quizapp.utils

import android.graphics.Color
import androidx.core.graphics.ColorUtils

class ColorUtil {
    companion object {
        fun dark(color: Int, darkening: Float): Int {
            return ColorUtils.blendARGB(color, Color.BLACK, darkening)
        }

        fun getColorFromInt(value: Int): Int {
            return when {
                value <= 0 -> Color.GRAY
                value in 1..24 -> ColorUtils.blendARGB(Color.GRAY, Color.GREEN, value / 25f)
                value in 25..49 -> ColorUtils.blendARGB(Color.GREEN, Color.YELLOW, (value - 25) / 25f)
                value in 50..74 -> ColorUtils.blendARGB(Color.YELLOW, Color.RED, (value - 50) / 25f)
                value in 75..99 -> ColorUtils.blendARGB(Color.RED, Color.MAGENTA, (value - 75) / 25f)
                else -> Color.MAGENTA
            }
        }
    }
}