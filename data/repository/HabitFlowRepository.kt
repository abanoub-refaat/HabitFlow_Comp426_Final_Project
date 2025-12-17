package com.example.habitflow.data.repository

import com.example.habitflow.data.dao.HabitDao
import com.example.habitflow.data.dao.JournalDao
import com.example.habitflow.data.model.Habit
import com.example.habitflow.data.model.JournalEntry
import kotlinx.coroutines.flow.Flow

class HabitFlowRepository(
    private val habitDao: HabitDao,
    private val journalDao: JournalDao
) {
    fun getAllHabits(): Flow<List<Habit>> = habitDao.getAllHabits()
    
    suspend fun insertHabit(habit: Habit) = habitDao.insertHabit(habit)
    
    suspend fun updateHabit(habit: Habit) = habitDao.updateHabit(habit)
    
    suspend fun deleteHabit(habit: Habit) = habitDao.deleteHabit(habit)

    fun getAllEntries(): Flow<List<JournalEntry>> = journalDao.getAllEntries()
    
    suspend fun insertEntry(entry: JournalEntry) = journalDao.insertEntry(entry)
    
    suspend fun updateEntry(entry: JournalEntry) = journalDao.updateEntry(entry)
    
    suspend fun deleteEntry(entry: JournalEntry) = journalDao.deleteEntry(entry)
}
