package com.example.quizapp.utils

import android.content.Context
import com.example.quizapp.dto.Topic
import com.google.gson.Gson
import java.util.logging.Logger

class TopicFileLoader(private val context: Context) {

    private val logger = Logger.getLogger(this::class.simpleName!!)
    fun loadFile(fileId: String): Topic {
        logger.info("Loading file: $fileId")
        val inputStream = context.assets.open(fileId)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        return Gson().fromJson(jsonString, Topic::class.java)
    }
}