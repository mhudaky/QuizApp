package com.mhudaky.quizapp.main

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.mhudaky.quizapp.dto.Topic
import com.mhudaky.quizapp.dto.TopicNode
import com.mhudaky.quizapp.utils.SharedPreferencesHelperForMain

class TopicTreeLoader(private val resources: Resources, private val prefsHelper: SharedPreferencesHelperForMain) : ViewModel() {

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
                childNode.topicIdentifier.score = calculateScore(childNode)
            }
        }
    }

    private fun calculateScore(node: TopicNode): Int {
        if (node.topicIdentifier.hasSubTopics) {
            val subTopicScores = node.children.map { calculateScore(it) }
            return subTopicScores.average().toInt()
        }
        return prefsHelper.getScoreForTopic(node.topicIdentifier.name)
    }

    fun getRoot(): TopicNode {
        return root
    }
}