package com.example.habitflow.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.habitflow.data.dao.HabitDao
import com.example.habitflow.data.dao.JournalDao
import com.example.habitflow.data.model.Habit
import com.example.habitflow.data.model.JournalEntry

@Database(
    entities = [Habit::class, JournalEntry::class],
    version = 1,
    exportSchema = false
)
abstract class HabitFlowDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun journalDao(): JournalDao

    companion object {
        @Volatile
        private var INSTANCE: HabitFlowDatabase? = null

        fun getDatabase(context: Context): HabitFlowDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitFlowDatabase::class.java,
                    "habit_flow_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
