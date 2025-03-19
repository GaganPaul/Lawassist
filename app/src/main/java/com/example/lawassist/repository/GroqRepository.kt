package com.example.lawassist.repository

import com.example.lawassist.model.GroqResponse
import com.example.lawassist.network.GroqApiService
import com.example.lawassist.network.GroqRequest
import com.example.lawassist.network.Message
import com.example.lawassist.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class GroqRepository {

    private val groqApiService: GroqApiService =
        RetrofitClient.instance.create(GroqApiService::class.java)

    suspend fun queryGroqLlama(prompt: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val systemPrompt = """
                    You are LawAssist, an AI assistant that provides quick, clear, and easy-to-understand answers about laws and government schemes and accessibility services in India.
                    - Keep responses under 50 words.
                    - Prioritize clarity and efficiency.
                    - Mention key schemes like Ayushman Bharat and Sugamya Bharat Abhiyan.
                    - Ensure responses are actionable and useful for Indian users.
                    - Use simple language, avoiding unnecessary details.
                    - If the user makes a spelling mistake, assume the correct spelling and respond accordingly.
                """.trimIndent()

                // Create the request body using the GroqRequest data class
                val requestBody = GroqRequest(
                    model = "llama3-8b-8192",
                    messages = listOf(
                        Message(role = "system", content = systemPrompt),
                        Message(role = "user", content = prompt)
                    ),
                    max_tokens = 180,
                    temperature = 0.5,
                    top_p = 0.7
                )

                // Make API call
                val response: GroqResponse = groqApiService.getGroqResponse(requestBody)

                response.choices.firstOrNull()?.message?.content ?: "No response from AI."

            } catch (e: HttpException) {
                "Error: ${e.message}"
            } catch (e: Exception) {
                "An error occurred: ${e.localizedMessage}"
            }
        }
    }
}
