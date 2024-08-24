package com.mhudaky.quizapp.dto

class TopicNode(val topicIdentifier: Topic) {
    val children: MutableList<TopicNode> = mutableListOf()
}