package com.example.quizapp.question

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.dto.Question
import com.example.quizapp.dto.Topic
import com.example.quizapp.dto.TopicDifficultyDTO
import com.example.quizapp.utils.SharedPreferencesHelper
import java.util.logging.Logger

class QuestionViewModel(private val topic: Topic, private val prefsHelper: SharedPreferencesHelper) : ViewModel() {

    var question = MutableLiveData<Question>()
    var indexLiveData = MutableLiveData<Int>()
    private var currentQuestionIndex = 0
    private val logger = Logger.getLogger(this::class.simpleName!!)
    val gameStats = GameStats(topic.name, prefsHelper)
    val answerChecker = AnswerChecker(gameStats)

    init {
        currentQuestionIndex = prefsHelper.getCurrentQuestionIndex(createTopicDifficultyDTO())
        question.value = getNextQuestion()
        logger.info("QuestionViewModel created: $this")
    }

    fun loadNextQuestion() {
        question.value = getNextQuestion()
    }

    private fun getNextQuestion(): Question {
        answerChecker.newQuestion()
        currentQuestionIndex = prefsHelper.getCurrentQuestionIndex(createTopicDifficultyDTO())
        return topic.getQuestions(gameStats.getDifficulty())[getNextQuestionIndex()]
    }

    private fun getNextQuestionIndex(): Int {
        currentQuestionIndex++
        if (currentQuestionIndex >= topic.getQuestions(gameStats.getDifficulty()).size) {
            currentQuestionIndex = 0
        }
        prefsHelper.saveCurrentQuestionIndex(createTopicDifficultyDTO(), currentQuestionIndex)
        indexLiveData.value = currentQuestionIndex
        return currentQuestionIndex
    }

    fun checkAnswer(selectedAnswer: String): Boolean {
        return answerChecker.checkAnswer(selectedAnswer, question.value!!)
    }

    private fun createTopicDifficultyDTO(): TopicDifficultyDTO {
        return TopicDifficultyDTO(topic.name, gameStats.getDifficulty())
    }

    fun onTimeIsUp() {
        answerChecker.onTimeIsUp()
    }
}