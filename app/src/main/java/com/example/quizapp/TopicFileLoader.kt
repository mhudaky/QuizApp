package com.example.quizapp

import android.content.Context
import com.google.gson.Gson

class TopicFileLoader(private val context: Context) {
    fun loadFile(fileId: String): Topic {
        val inputStream = context.assets.open(fileId)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        return Gson().fromJson(jsonString, Topic::class.java)
    }
}