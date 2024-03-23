package com.example.quizapp

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {

    private val PREFS_NAME = "com.example.quizapp"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveStreak(topicId: String, difficulty: Difficulty, streak: Int) {
        val editor = prefs.edit()
        editor.putInt("${topicId}_${difficulty.name}_streak", streak)
        editor.apply()
    }

    fun getStreak(topicId: String, difficulty: Difficulty): Int {
        return prefs.getInt("${topicId}_${difficulty.name}_streak", 0)
    }

    fun savePoints(topicId: String, difficulty: Difficulty, points: Int) {
        val editor = prefs.edit()
        editor.putInt("${topicId}_${difficulty.name}_points", points)
        editor.apply()
    }

    fun getPoints(topicId: String, difficulty: Difficulty): Int {
        return prefs.getInt("${topicId}_${difficulty.name}_points", 0)
    }

    fun saveCurrentQuestionIndex(topicId: String, difficulty: Difficulty, index: Int) {
        val editor = prefs.edit()
        editor.putInt("${topicId}_${difficulty.name}_current_question_index", index)
        editor.apply()
    }

    fun getCurrentQuestionIndex(topicId: String, difficulty: Difficulty): Int {
        return prefs.getInt("${topicId}_${difficulty.name}_current_question_index", 0)
    }

    fun resetStats() {
        prefs.edit().clear().apply()
    }
}