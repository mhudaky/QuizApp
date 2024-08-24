package com.mhudaky.quizapp.main

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.mhudaky.quizapp.dto.Topic
import com.mhudaky.quizapp.dto.TopicNode

class TopicTreeLoader(private val resources: Resources) : ViewModel() {

    private val root: TopicNode = TopicNode(Topic("root", "quiz", true))

    init {
        loadTopics(root)
    }

    private fun loadTopics(node: TopicNode) {
        val assetManager = resources.assets
        val files = assetManager.list(node.topicIdentifier.filePath)
        if (files != null) {
            for (filename in files) {
                val filePath = "${node.topicIdentifier.filePath}/$filename"
                val topicName = filename.replace(".json", "")
                val hasSubTopics = !filePath.endsWith(".json")
                val childNode = TopicNode(Topic(topicName, filePath, hasSubTopics))
                node.children.add(childNode)
                if (hasSubTopics) {
                    loadTopics(childNode)
                }
            }
        }
    }

    fun getRoot(): TopicNode {
        return root
    }
}