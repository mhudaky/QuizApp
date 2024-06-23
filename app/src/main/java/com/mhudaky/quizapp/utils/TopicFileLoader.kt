package com.mhudaky.quizapp.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mhudaky.quizapp.dto.MultiChoiceTopic
import com.mhudaky.quizapp.dto.SwipeTopic
import com.mhudaky.quizapp.enums.QuestionType
import java.util.logging.Logger

class TopicFileLoader(private val context: Context) {

    private val logger = Logger.getLogger(this::class.simpleName!!)
    fun loadMultiChoiceTopic(fileId: String): MultiChoiceTopic {
        val jsonObject = getJsonObject(fileId)
        val keyValue = jsonObject.get(QuestionType.MULTI_CHOICE.nameInJson)
        return Gson().fromJson(keyValue, MultiChoiceTopic::class.java)
    }

    fun loadSwipesTopic(fileId: String): SwipeTopic {
        val jsonObject = getJsonObject(fileId)
        val keyValue = jsonObject.get(QuestionType.SWIPE.nameInJson)
        return Gson().fromJson(keyValue, SwipeTopic::class.java)
    }

    private fun getJsonObject(fileId: String): JsonObject {
        logger.info("Loading file: $fileId")
        val inputStream = context.assets.open(fileId)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val jsonObject = JsonParser.parseString(jsonString).asJsonObject
        return jsonObject
    }


}