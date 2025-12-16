package com.example.habitflow.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habitflow.data.repository.HabitFlowRepository

class HabitFlowViewModelFactory(
    private val repository: HabitFlowRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitFlowViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HabitFlowViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
