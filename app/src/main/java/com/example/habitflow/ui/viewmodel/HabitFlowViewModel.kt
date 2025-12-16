package com.example.habitflow.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitflow.data.model.Habit
import com.example.habitflow.data.model.JournalEntry
import com.example.habitflow.data.repository.HabitFlowRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HabitFlowViewModel(private val repository: HabitFlowRepository) : ViewModel() {
    
    val habits = repository.getAllHabits().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    
    val journalEntries = repository.getAllEntries().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun toggleHabit(habit: Habit) {
        viewModelScope.launch {
            val updatedHabit = if (habit.isCompletedToday) {
                habit.copy(
                    isCompletedToday = false,
                    currentStreak = maxOf(0, habit.currentStreak - 1)
                )
            } else {
                val newStreak = habit.currentStreak + 1
                habit.copy(
                    isCompletedToday = true,
                    currentStreak = newStreak,
                    bestStreak = maxOf(habit.bestStreak, newStreak),
                    totalCompletions = habit.totalCompletions + 1
                )
            }
            repository.updateHabit(updatedHabit)
        }
    }

    fun addHabit(name: String, description: String) {
        viewModelScope.launch {
            repository.insertHabit(Habit(name = name, description = description))
        }
    }

    fun addJournalEntry(title: String, content: String) {
        viewModelScope.launch {
            repository.insertEntry(JournalEntry(title = title, content = content))
        }
    }
}
