package com.mhudaky.quizapp.quiz.multichoice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mhudaky.quizapp.dto.MultiChoiceTopic
import com.mhudaky.quizapp.utils.SharedPreferencesHelper

class QuestionViewModelFactory(private val topic: MultiChoiceTopic, private val prefsHelper: SharedPreferencesHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuestionViewModel(topic, prefsHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}