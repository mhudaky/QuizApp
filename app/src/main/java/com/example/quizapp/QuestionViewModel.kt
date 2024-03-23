package com.example.quizapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuestionViewModel(private val topic: Topic, private val prefsHelper: SharedPreferencesHelper) : ViewModel() {

    var score = MutableLiveData<Int>()
    var streak = MutableLiveData<Int>()
    var question = MutableLiveData<Question>()
    private var currentQuestionIndex = 0
    private var currentDifficulty = Difficulty.EASY
    private var timeIsUp = false
    private var firstGuess = true

    init {
        score.value = prefsHelper.getPoints(topic.name)
        streak.value = prefsHelper.getStreak(topic.name)
        currentQuestionIndex = prefsHelper.getCurrentQuestionIndex(topic.name)
        question.value = getNextQuestion()
    }

    fun loadNextQuestion() {
        question.value = getNextQuestion()
        updateStreak(0)
        timeIsUp = false
        firstGuess = true
    }

    fun checkAnswer(selectedAnswer: String): Boolean {
        val isCorrect = selectedAnswer == question.value?.correct
        if (isCorrect) {
            onRightGuess()
        } else {
            onWrongGuess()
        }
        return isCorrect
    }

    private fun getNextQuestion() = topic.getQuestions(currentDifficulty)[getNextQuestionIndex()]

    private fun onRightGuess() {
        if(firstGuess && !timeIsUp) {
            updateScore(score.value!! + 1)
            updateStreak(streak.value!! + 1)
        }
    }

    private fun onWrongGuess() {
        updateStreak(0)
        firstGuess = false
    }

    private fun updateScore(newScore: Int) {
        score.value = newScore
        prefsHelper.savePoints(topic.name, newScore)
    }

    private fun updateStreak(newStreak: Int) {
        streak.value = newStreak
        prefsHelper.savePoints(topic.name, newStreak)
    }

    private fun getNextQuestionIndex(): Int {
        currentQuestionIndex++
        if (currentQuestionIndex >= topic.getQuestions(currentDifficulty).size) {
            currentQuestionIndex = 0
        }
        prefsHelper.saveCurrentQuestionIndex(topic.name, currentQuestionIndex)
        return currentQuestionIndex
    }

    fun saveState() {
        prefsHelper.savePoints(topic.name, score.value!!)
        prefsHelper.saveStreak(topic.name, streak.value!!)
        prefsHelper.saveCurrentQuestionIndex(topic.name, getNextQuestionIndex())
    }

    fun onTimeUp() {
        timeIsUp = true
        updateStreak(0)
    }
}