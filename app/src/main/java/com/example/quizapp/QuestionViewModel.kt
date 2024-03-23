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
        score.value = prefsHelper.getPoints(createTopicDifficultyDTO())
        streak.value = prefsHelper.getStreak(createTopicDifficultyDTO())
        currentQuestionIndex = prefsHelper.getCurrentQuestionIndex(createTopicDifficultyDTO())
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

    private fun getNextQuestion(): Question {
        setDifficulty()
        return topic.getQuestions(currentDifficulty)[getNextQuestionIndex()]
    }

    private fun setDifficulty() {
        currentDifficulty = when (streak.value) {
            in 0..2 -> Difficulty.EASY
            in 3..5 -> Difficulty.MEDIUM
            else -> Difficulty.HARD
        }
        currentQuestionIndex = prefsHelper.getCurrentQuestionIndex(createTopicDifficultyDTO())
    }

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
        prefsHelper.savePoints(createTopicDifficultyDTO(), newScore)
    }

    private fun updateStreak(newStreak: Int) {
        streak.value = newStreak
        prefsHelper.savePoints(createTopicDifficultyDTO(), newStreak)
    }

    private fun getNextQuestionIndex(): Int {
        currentQuestionIndex++
        if (currentQuestionIndex >= topic.getQuestions(currentDifficulty).size) {
            currentQuestionIndex = 0
        }
        prefsHelper.saveCurrentQuestionIndex(createTopicDifficultyDTO(), currentQuestionIndex)
        return currentQuestionIndex
    }

    private fun createTopicDifficultyDTO(): TopicDifficultyDTO {
        return TopicDifficultyDTO(topic.name, currentDifficulty)
    }

    fun saveState() {
        prefsHelper.savePoints(createTopicDifficultyDTO(), score.value!!)
        prefsHelper.saveStreak(createTopicDifficultyDTO(), streak.value!!)
        prefsHelper.saveCurrentQuestionIndex(createTopicDifficultyDTO(), getNextQuestionIndex())
    }

    fun onTimeIsUp() {
        timeIsUp = true
        updateStreak(0)
    }
}