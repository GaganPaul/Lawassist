package com.example.lawassist.model

import com.google.gson.annotations.SerializedName

data class GroqResponse(
    @SerializedName("choices") val choices: List<Choice>
)

data class Choice(
    @SerializedName("message") val message: Message
)

data class Message(
    @SerializedName("role") val role: String,
    @SerializedName("content") val content: String
)
