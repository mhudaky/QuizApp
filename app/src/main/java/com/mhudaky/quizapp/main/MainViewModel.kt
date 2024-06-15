package com.mhudaky.quizapp.main

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.mhudaky.quizapp.dto.TopicIdentifier
import java.util.logging.Logger

class MainViewModel(private val resources: Resources) : ViewModel() {

    private val topicIdentifiers: MutableList<TopicIdentifier> = mutableListOf()
    private val logger = Logger.getLogger(this::class.simpleName!!)
    private lateinit var currentPath: String

    init {
        reset()
    }

    fun reset() {
        currentPath = "quiz"
        loadTopics()
    }

    private fun loadTopics() {
        topicIdentifiers.clear()
        val assetManager = resources.assets
        val files = assetManager.list(currentPath)
        if (files != null) {
            for (filename in files) {
                val filePath = "$currentPath/$filename"
                val topicName = filename.replace(".json", "")
                if (filePath.endsWith(".json")) {
                    topicIdentifiers.add(TopicIdentifier(topicName, filePath, false))
                } else {
                    topicIdentifiers.add(TopicIdentifier(topicName, filePath, true))
                }
            }
        }
    }

    fun getTopicIdentifiers(): List<TopicIdentifier> {
        return topicIdentifiers
    }

    fun choseTopic(topicIdentifier: TopicIdentifier) {
        logger.info("Chose topic: ${topicIdentifier.name}")
        if (topicIdentifier.hasSubTopics) {
            navigateTo(topicIdentifier.filePath)
        }
    }

    private fun navigateTo(path: String) {
        currentPath = path
        loadTopics()
    }
}