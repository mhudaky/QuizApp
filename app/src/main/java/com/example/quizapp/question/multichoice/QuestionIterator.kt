package com.example.quizapp.question.multichoice

import androidx.lifecycle.MutableLiveData
import com.example.quizapp.dto.Difficulty
import com.example.quizapp.dto.Question
import com.example.quizapp.dto.Topic
import com.example.quizapp.dto.TopicDifficultyDTO
import com.example.quizapp.utils.SharedPreferencesHelper

class QuestionIterator(private val topic: Topic, private val prefsHelper: SharedPreferencesHelper) {


    var question = MutableLiveData<Question>()
    var indexLiveData = MutableLiveData<Int>()
    private var currentQuestionIndex = 0

    fun loadQuestion(difficulty: Difficulty) {
        currentQuestionIndex = prefsHelper.getCurrentQuestionIndex(createTopicDifficultyDTO(difficulty))
        question.value = topic.getQuestions(difficulty)[getNextQuestionIndex(difficulty)]
    }

    fun getQuestion(): Question {
        return question.value!!
    }

    private fun getNextQuestionIndex(difficulty: Difficulty): Int {
        currentQuestionIndex++
        if (currentQuestionIndex >= topic.getQuestions(difficulty).size) {
            currentQuestionIndex = 0
        }
        prefsHelper.saveCurrentQuestionIndex(createTopicDifficultyDTO(difficulty), currentQuestionIndex)
        indexLiveData.value = currentQuestionIndex
        return currentQuestionIndex
    }

    private fun createTopicDifficultyDTO(difficulty: Difficulty): TopicDifficultyDTO {
        return TopicDifficultyDTO(topic.name, difficulty)
    }
}