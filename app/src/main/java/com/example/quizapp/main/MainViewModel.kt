package com.example.quizapp.main

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.example.quizapp.dto.TopicIdentifier

class MainViewModel(private val resources: Resources) : ViewModel() {

    private val topics: MutableList<TopicIdentifier> = mutableListOf()
    private var currentPath: String = "questions"

    init {
        loadTopics()
    }

    private fun loadTopics() {
        topics.clear()
        val assetManager = resources.assets
        val files = assetManager.list(currentPath)
        if (files != null) {
            for (filename in files) {
                val filePath = "$currentPath/$filename"
                topics.add(TopicIdentifier(filename, filePath))
            }
        }
    }

    fun getTopicNames(): List<String> {
        return topics.map { it.name }
    }

    fun choseTopic(topicName: String): TopicIdentifier {
        return topics.find { it.name == topicName }!!
    }
}