package com.example.quizapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.logging.Logger

class QuestionViewModel(private val topic: Topic, private val prefsHelper: SharedPreferencesHelper) : ViewModel() {

    var score = MutableLiveData<Int>()
    var streak = MutableLiveData<Int>()
    var question = MutableLiveData<Question>()
    var difficultyLiveData = MutableLiveData<Difficulty>()
    var indexLiveData = MutableLiveData<Int>()
    private var currentQuestionIndex = 0
    private var currentDifficulty = Difficulty.EASY
    private var timeIsUp = false
    private var guessedAlready = false
    private val logger = Logger.getLogger(this::class.simpleName!!)


    init {
        score.value = prefsHelper.getPoints(createTopicDifficultyDTO())
        streak.value = prefsHelper.getStreak(createTopicDifficultyDTO())
        currentQuestionIndex = prefsHelper.getCurrentQuestionIndex(createTopicDifficultyDTO())
        question.value = getNextQuestion()
        logger.info("QuestionViewModel created: $this")
    }

    fun loadNextQuestion() {
        if (!guessedAlready) {
            updateStreak(0)
        }
        question.value = getNextQuestion()
        timeIsUp = false
        guessedAlready = false
    }

    fun checkAnswer(selectedAnswer: String): Boolean {
        val isCorrect = selectedAnswer == question.value?.correct
        if (isCorrect) {
            onRightGuess()
        } else {
            onWrongGuess()
        }
        guessedAlready = true
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
        logger.info("Difficulty set to: ${this.currentDifficulty}")
        difficultyLiveData.value = currentDifficulty
        currentQuestionIndex = prefsHelper.getCurrentQuestionIndex(createTopicDifficultyDTO())
    }

    private fun onRightGuess() {
        if(!guessedAlready && !timeIsUp) {
            increaseScore()
            updateStreak(streak.value!! + 1)
        }
    }

    private fun increaseScore() {
        val increment = when (currentDifficulty) {
            Difficulty.EASY -> 1
            Difficulty.MEDIUM -> 2
            Difficulty.HARD -> 4
        }
        updateScore(score.value!! + increment)
    }

    private fun onWrongGuess() {
        updateStreak(0)
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
        indexLiveData.value = currentQuestionIndex
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