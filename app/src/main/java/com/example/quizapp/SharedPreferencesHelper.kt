package com.example.quizapp

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {

    private val PREFS_NAME = "com.example.quizapp"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveStreak(dto: TopicDifficultyDTO, streak: Int) {
        val editor = prefs.edit()
        editor.putInt("${dto.topicName}_${dto.difficulty.name}_streak", streak)
        editor.apply()
    }

    fun getStreak(dto: TopicDifficultyDTO): Int {
        return prefs.getInt("${dto.topicName}_${dto.difficulty.name}_streak", 0)
    }

    fun savePoints(dto: TopicDifficultyDTO, points: Int) {
        val editor = prefs.edit()
        editor.putInt("${dto.topicName}_${dto.difficulty.name}_points", points)
        editor.apply()
    }

    fun getPoints(dto: TopicDifficultyDTO): Int {
        return prefs.getInt("${dto.topicName}_${dto.difficulty.name}_points", 0)
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