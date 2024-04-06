package com.example.quizapp.question.swipe

import android.content.Intent
import android.graphics.Color
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.R
import com.example.quizapp.main.MainActivity

class ButtonInitializer(private val activity: AppCompatActivity, private val viewModel: SwipeViewModel) {

    private val answersField: LinearLayout = activity.findViewById(R.id.answers)

    fun initializeButtons(): List<Button> {
        val buttonIds = listOf(
            R.id.answer_button_1,
            R.id.answer_button_2,
            R.id.answer_button_3,
            R.id.answer_button_4
        )

        val answerButtons = buttonIds.map { id ->
            val button = activity.findViewById<Button>(id)
            setClickListener(button)
            button
        }

        val returnButton = activity.findViewById<Button>(R.id.button_main)
        returnButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
        }

        val nextButton = activity.findViewById<Button>(R.id.next)
        nextButton.setOnClickListener {
            viewModel.loadNextQuestion()
        }

        return answerButtons
    }

    private fun setClickListener(button: Button) {
        button.setOnClickListener {
            val isCorrect = viewModel.checkAnswer(button.text.toString())
            if (isCorrect) {
                button.setBackgroundColor(Color.GREEN)
                this.disableAllAnswerButtons()
            } else {
                button.setBackgroundColor(Color.RED)
                button.isEnabled = false
            }
        }
    }

    private fun disableAllAnswerButtons() {
        for (i in 0 until answersField.childCount) {
            val child = answersField.getChildAt(i)
            if (child is Button) {
                child.isEnabled = false
            }
        }
    }
}