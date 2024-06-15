package com.example.quizapp.quiz.swipe

import androidx.lifecycle.MutableLiveData
import com.example.quizapp.dto.Swipe
import com.example.quizapp.dto.SwipeTopic
import com.example.quizapp.dto.TopicDto
import com.example.quizapp.enums.Difficulty
import com.example.quizapp.utils.SharedPreferencesHelper

class SwipeIterator(private val topic: SwipeTopic, private val prefsHelper: SharedPreferencesHelper) {


    var swipe = MutableLiveData<Swipe>()
    var indexLiveData = MutableLiveData<Int>()
    private var currentSwipeIndex = 0

    fun loadSwipe(difficulty: Difficulty) {
        currentSwipeIndex = prefsHelper.getCurrentIndex(createTopicDifficultyDTO(difficulty))
        swipe.value = topic.getSwipes(difficulty)[getNextSwipeIndex(difficulty)]
    }

    fun getSwipe(): Swipe {
        return swipe.value!!
    }

    private fun getNextSwipeIndex(difficulty: Difficulty): Int {
        currentSwipeIndex++
        if (currentSwipeIndex >= topic.getSwipes(difficulty).size) {
            currentSwipeIndex = 0
        }
        prefsHelper.saveCurrentIndex(createTopicDifficultyDTO(difficulty), currentSwipeIndex)
        indexLiveData.value = currentSwipeIndex
        return currentSwipeIndex
    }

    private fun createTopicDifficultyDTO(difficulty: Difficulty): TopicDto {
        return TopicDto(topic.name, difficulty)
    }
}