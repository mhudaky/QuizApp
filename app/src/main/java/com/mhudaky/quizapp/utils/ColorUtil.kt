package com.mhudaky.quizapp.utils

import android.graphics.Color
import androidx.core.graphics.ColorUtils

class ColorUtil {
    companion object {

        val LIGHT_GREY = Color.parseColor("#D3D3D3")
        val ORANGE = Color.parseColor("#FFA500")
        val RED = Color.parseColor("#8B0000")
        fun dark(color: Int, darkening: Float): Int {
            return ColorUtils.blendARGB(color, Color.BLACK, darkening)
        }

        fun getColorFromInt(value: Int): Int {
            return when {
                value <= 0 -> LIGHT_GREY
                value in 1..24 -> ColorUtils.blendARGB(LIGHT_GREY, Color.GREEN, value / 25f)
                value in 25..49 -> ColorUtils.blendARGB(Color.GREEN, ORANGE, (value - 25) / 25f)
                value in 50..74 -> ColorUtils.blendARGB(ORANGE, RED, (value - 50) / 25f)
                value in 75..99 -> ColorUtils.blendARGB(RED, Color.MAGENTA, (value - 75) / 25f)
                else -> Color.MAGENTA
            }
        }
    }
}