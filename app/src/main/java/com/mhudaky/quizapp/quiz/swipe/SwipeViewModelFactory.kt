package com.mhudaky.quizapp.quiz.swipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mhudaky.quizapp.dto.SwipeTopic
import com.mhudaky.quizapp.utils.SharedPreferencesHelper

class SwipeViewModelFactory(private val topic: SwipeTopic, private val prefsHelper: SharedPreferencesHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SwipeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SwipeViewModel(topic, prefsHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}