package com.mhudaky.quizapp.quiz.multichoice

import androidx.lifecycle.MutableLiveData
import com.mhudaky.quizapp.enums.Difficulty
import com.mhudaky.quizapp.dto.MultiChoice
import com.mhudaky.quizapp.dto.MultiChoiceTopic
import com.mhudaky.quizapp.dto.TopicDto
import com.mhudaky.quizapp.utils.SharedPreferencesHelper

class QuestionIterator(private val topic: MultiChoiceTopic, private val prefsHelper: SharedPreferencesHelper) {


    var question = MutableLiveData<MultiChoice>()
    var indexLiveData = MutableLiveData<Int>()
    private var currentQuestionIndex = 0

    fun loadQuestion(difficulty: Difficulty) {
        currentQuestionIndex = prefsHelper.getCurrentIndex(createTopicDifficultyDTO(difficulty))
        question.value = topic.getQuestions(difficulty)[getNextQuestionIndex(difficulty)]
    }

    fun getQuestion(): MultiChoice {
        return question.value!!
    }

    private fun getNextQuestionIndex(difficulty: Difficulty): Int {
        currentQuestionIndex++
        if (currentQuestionIndex >= topic.getQuestions(difficulty).size) {
            currentQuestionIndex = 0
        }
        prefsHelper.saveCurrentIndex(createTopicDifficultyDTO(difficulty), currentQuestionIndex)
        indexLiveData.value = currentQuestionIndex
        return currentQuestionIndex
    }

    private fun createTopicDifficultyDTO(difficulty: Difficulty): TopicDto {
        return TopicDto(topic.name, difficulty)
    }
}