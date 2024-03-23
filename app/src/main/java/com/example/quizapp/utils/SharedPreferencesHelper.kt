package com.example.quizapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.quizapp.dto.TopicDifficultyDTO

class SharedPreferencesHelper(context: Context) {

    private val PREFS_NAME = "com.example.quizapp"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveStreak(topicId: String, streak: Int) {
        val editor = prefs.edit()
        editor.putInt("${topicId}_streak", streak)
        editor.apply()
    }

    fun getStreak(topicId: String): Int {
        return prefs.getInt("${topicId}_streak", 0)
    }

    fun savePoints(topicId: String, points: Int) {
        val editor = prefs.edit()
        editor.putInt("${topicId}_points", points)
        editor.apply()
    }

    fun getPoints(topicId: String): Int {
        return prefs.getInt("${topicId}_points", 0)
    }

    fun saveCurrentQuestionIndex(dto: TopicDifficultyDTO, index: Int) {
        val editor = prefs.edit()
        editor.putInt("${dto.topicName}_${dto.difficulty.name}_current_question_index", index)
        editor.apply()
    }

    fun getCurrentQuestionIndex(dto: TopicDifficultyDTO): Int {
        return prefs.getInt("${dto.topicName}_${dto.difficulty.name}_current_question_index", 0)
    }

    fun resetStats() {
        prefs.edit().clear().apply()
    }
}