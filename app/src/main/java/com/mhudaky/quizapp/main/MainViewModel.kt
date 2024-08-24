package com.mhudaky.quizapp.main

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.mhudaky.quizapp.dto.Topic
import com.mhudaky.quizapp.dto.TopicNode
import java.util.logging.Logger

class MainViewModel(private val resources: Resources) : ViewModel() {

    private val root: TopicNode = TopicNode(Topic("root", "quiz", true))
    private val logger = Logger.getLogger(this::class.simpleName!!)

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

    fun getTopicNode(path: String): TopicNode? {
        return findNode(root, path)
    }

    private fun findNode(node: TopicNode, path: String): TopicNode? {
        if (node.topicIdentifier.filePath == path) {
            return node
        }
        for (child in node.children) {
            val result = findNode(child, path)
            if (result != null) {
                return result
            }
        }
        return null
    }
}