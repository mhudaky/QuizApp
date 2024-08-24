package com.mhudaky.quizapp.main

import android.content.Context
import android.content.res.ColorStateList
import android.widget.Button
import com.mhudaky.quizapp.dto.TopicNode
import com.mhudaky.quizapp.utils.ColorUtil

class ButtonCreator(private val context: Context) {

    fun createButton(node: TopicNode, onClickListener: (TopicNode) -> Unit): Button {
        val button = Button(context)
        button.text = node.topicIdentifier.name.uppercase()
        setBackgroundColorOnButton(button, node)
        button.setOnClickListener {
            onClickListener(node)
        }
        return button
    }

    private fun setBackgroundColorOnButton(button: Button, node: TopicNode) {
        val color = calculateColor(node)
        setButtonColor(button, color)
    }

    private fun calculateColor(node: TopicNode): Int {
        val score = node.topicIdentifier.score
        return ColorUtil.getColorFromInt(score)
    }

    private fun setButtonColor(button: Button, color: Int) {
        button.setBackgroundTintList(ColorStateList.valueOf(color))
    }
}