package com.example.quizapp

import android.content.res.Resources
import com.google.gson.Gson

class TopicFileLoader(private val resources: Resources) {
    fun loadFile(fileId: Int): Topic {
        val inputStream = resources.openRawResource(fileId)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        return Gson().fromJson(jsonString, Topic::class.java)
    }
}