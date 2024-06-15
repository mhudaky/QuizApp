package com.mhudaky.quizapp.quiz.swipe

import android.content.Intent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mhudaky.quizapp.R
import com.mhudaky.quizapp.main.MainActivity

class ButtonInitializer(private val activity: AppCompatActivity, private val viewModel: SwipeViewModel) {

    fun initializeButtons() {
        val returnButton = activity.findViewById<Button>(R.id.button_main)
        returnButton.setOnClickListener {
            viewModel.answerChecker.quitQuiz()
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
        }
        val nextButton = activity.findViewById<Button>(R.id.next)
        nextButton.setOnClickListener {
            viewModel.loadNextSwipe()
        }
    }
}