package com.mhudaky.quizapp.utils

import android.graphics.Color
import androidx.core.graphics.ColorUtils

class ColorUtil {
    companion object {

        val lightGrey = Color.parseColor("#D3D3D3")
        val ORANGE = Color.parseColor("#FFAC1C")
        fun dark(color: Int, darkening: Float): Int {
            return ColorUtils.blendARGB(color, Color.BLACK, darkening)
        }

        fun getColorFromInt(value: Int): Int {
            return when {
                value <= 0 -> lightGrey
                value in 1..24 -> ColorUtils.blendARGB(lightGrey, Color.GREEN, value / 25f)
                value in 25..49 -> ColorUtils.blendARGB(Color.GREEN, ORANGE, (value - 25) / 25f)
                value in 50..74 -> ColorUtils.blendARGB(ORANGE, Color.RED, (value - 50) / 25f)
                value in 75..99 -> ColorUtils.blendARGB(Color.RED, Color.MAGENTA, (value - 75) / 25f)
                else -> Color.MAGENTA
            }
        }
    }
}