// LawViewModelFactory.kt
package com.example.lawassist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lawassist.repository.LawRepository

class LawViewModelFactory(private val repository: LawRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LawViewModel::class.java)) {
            return LawViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
