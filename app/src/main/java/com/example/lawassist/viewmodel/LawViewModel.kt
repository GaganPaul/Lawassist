package com.example.lawassist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.lawassist.repository.LawRepository
import kotlinx.coroutines.launch
import com.example.lawassist.repository.GroqRepository
import com.example.lawassist.database.LawEntity


class LawViewModel(private val lawRepository: LawRepository) : ViewModel() {

    var lawsList = mutableStateOf<List<LawEntity>>(emptyList())
        private set

    var aiResponse = mutableStateOf("") // AI response to show in UI

    private val groqRepository = GroqRepository() // Assuming you have a GroqRepository

    fun insertLaw(law: LawEntity) {
        viewModelScope.launch {
            lawRepository.insertLaw(law)
        }
    }

    fun searchLaws(query: String) {
        viewModelScope.launch {
            lawsList.value = lawRepository.searchLaws(query)
        }
    }

    fun getAllLaws() {
        viewModelScope.launch {
            lawsList.value = lawRepository.getAllLaws()
        }
    }

    fun deleteLaw(law: LawEntity) {
        viewModelScope.launch {
            lawRepository.deleteLaw(law)
        }
    }

    // Function to handle the Groq Llama query
    fun queryGroqLlama(prompt: String) {
        viewModelScope.launch {
            // Assuming GroqRepository returns a String response
            val response = groqRepository.queryGroqLlama(prompt)
            aiResponse.value = response // Update the AI response in the UI
        }
    }
}
