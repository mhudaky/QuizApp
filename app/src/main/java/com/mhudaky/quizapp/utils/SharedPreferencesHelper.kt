package com.mhudaky.quizapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.mhudaky.quizapp.dto.TopicDto
import com.mhudaky.quizapp.enums.QuestionType

class SharedPreferencesHelper(context: Context, questionType: QuestionType) {

    private val PREFS_NAME = "com.mhudaky.quizapp"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val questionTypeName = questionType.name

    fun saveStreak(topicId: String, streak: Int) {
        saveData(getStreakKey(topicId), streak)
    }

    fun getStreak(topicId: String): Int {
        return getData(getStreakKey(topicId))
    }

    fun saveScore(topicId: String, points: Int) {
        saveData(getScoreKey(topicId), points)
    }

    fun getScore(topicId: String): Int {
        return getData(getScoreKey(topicId))
    }

    fun saveCurrentIndex(dto: TopicDto, index: Int) {
        saveData(getIndexKey(dto), index)
    }

    fun getCurrentIndex(dto: TopicDto): Int {
        return getData(getIndexKey(dto))
    }

    private fun saveData(key: String, value: Int) {
        val editor = prefs.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    private fun getData(key: String): Int {
        return prefs.getInt(key, 0)
    }

    private fun getScoreKey(topicId: String): String {
        return "${questionTypeName}_${topicId}_score"
    }

    private fun getStreakKey(topicId: String): String {
        return "${questionTypeName}_${topicId}_streak"
    }

    private fun getIndexKey(dto: TopicDto): String {
        return "${questionTypeName}_${dto.topicName}_${dto.difficulty.name}_index"
    }
}