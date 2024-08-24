package com.mhudaky.quizapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.mhudaky.quizapp.enums.QuestionType

class SharedPreferencesHelperForMain(context: Context) {

    private val PREFS_NAME = "com.mhudaky.quizapp"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun resetStats() {
        prefs.edit().clear().apply()
    }

    fun getScoreForTopic(topicId: String): Int {
        val multiChoiceScore = getScore(QuestionType.MULTI_CHOICE.name, topicId)
        val swipeScore = getScore(QuestionType.SWIPE.name, topicId)
        return (multiChoiceScore + swipeScore) / 2
    }

    private fun getScore(questionTypeName: String, topicId: String): Int {
        return getData(getScoreKey(questionTypeName, topicId))
    }

    private fun getScoreKey(questionTypeName: String, topicId: String): String {
        return "${questionTypeName}_${topicId}_score"
    }

    private fun getData(key: String): Int {
        return prefs.getInt(key, 0)
    }
}