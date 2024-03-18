package com.example.quizapp

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    data class Topic(val name: String, val questions: List<Question>)
    data class Question(val question: String, val answers: List<String>, val correct: String)
    data class QuestionBank(val topics: List<Topic>)

    private lateinit var questionField: TextView
    private lateinit var answersField: LinearLayout
    private lateinit var nextButton: Button
    private lateinit var questionBank: QuestionBank

    private var currentQuestionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        questionField = findViewById(R.id.question)
        answersField = findViewById(R.id.answers)
        nextButton = findViewById(R.id.next)
        nextButton.setOnClickListener {
            loadNextQuestion()
        }

        // Load your topics and questions here
        val inputStream = resources.openRawResource(R.raw.questions)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        questionBank = Gson().fromJson(jsonString, QuestionBank::class.java)

        loadNextQuestion()
    }

    private fun loadNextQuestion() {
        currentQuestionIndex++

        if (currentQuestionIndex >= questionBank.topics[0].questions.size) {
            currentQuestionIndex = 0
        }

        val question = questionBank.topics[0].questions[currentQuestionIndex]

        loadQuestion(question)
    }

    private fun loadQuestion(question: Question) {
        questionField.text = question.question
        answersField.removeAllViews()
        question.answers.shuffled().forEach { answer ->
            val button = Button(this)
            button.text = answer
            button.setOnClickListener {
                checkAnswer(question, button.text.toString())
            }
            answersField.addView(button)
        }
    }

    private fun checkAnswer(question: Question, selectedAnswer: String) {
        val context = this

        val toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
        val toastView = toast.view

        if (selectedAnswer == question.correct) {
            // Correct answer
            toastView?.setBackgroundColor(0xFF00CC00.toInt()) // Green color
            toast.setText("✔️ Correct! The answer is $selectedAnswer.")
        } else {
            // Incorrect answer
            toastView?.setBackgroundColor(0xFFFF4444.toInt()) // Red color
            toast.setText("❌ Incorrect. Try again!")
        }

        // Set text color (optional)
        val textColor = 0xFFFFFFFF.toInt() // White color
        val textView = toastView?.findViewById<TextView>(android.R.id.message)
        textView?.setTextColor(textColor)

        toast.show()
    }
}
