package com.example.lawassist.network

// Request body data class for Groq API
data class GroqRequest(
    val model: String,          // The AI model you want to use, e.g., "llama3-8b"
    val messages: List<Message>, // List of messages to send in the request
    val max_tokens: Int,         // Maximum number of tokens to generate
    val temperature: Double,     // Temperature for randomness of responses
    val top_p: Double           // Top-p sampling (nucleus sampling)
)

data class Message(
    val role: String,  // "system" or "user"
    val content: String // Content of the message
)
