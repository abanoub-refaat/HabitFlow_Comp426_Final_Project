package com.example.habitflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.habitflow.data.database.HabitFlowDatabase
import com.example.habitflow.data.repository.HabitFlowRepository
import com.example.habitflow.ui.navigation.Screen
import com.example.habitflow.ui.navigation.bottomNavItems
import com.example.habitflow.ui.screens.*
import com.example.habitflow.ui.theme.HabitFlowTheme
import com.example.habitflow.ui.viewmodel.HabitFlowViewModel
import com.example.habitflow.ui.viewmodel.HabitFlowViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = HabitFlowDatabase.getDatabase(this)
        val repository = HabitFlowRepository(database.habitDao(), database.journalDao())
        
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            HabitFlowTheme(darkTheme = isDarkTheme) {
                MainScreen(
                    repository = repository,
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = { isDarkTheme = it }
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    repository: HabitFlowRepository,
    isDarkTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val viewModel: HabitFlowViewModel = viewModel(
        factory = HabitFlowViewModelFactory(repository)
    )
    val habits by viewModel.habits.collectAsState()
    val journalEntries by viewModel.journalEntries.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = NavigationBarDefaults.Elevation
            ) {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.screen.route,
                        onClick = {
                            if (currentRoute != item.screen.route) {
                                navController.navigate(item.screen.route) {
                                    popUpTo(Screen.Home.route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (currentRoute == item.screen.route) {
                                    item.selectedIcon
                                } else {
                                    item.unselectedIcon
                                },
                                contentDescription = item.getLabel()
                            )
                        },
                        label = {
                            Text(text = item.getLabel())
                        },
                        alwaysShowLabel = false
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    todayHabits = habits,
                    recentEntries = journalEntries,
                    onToggleHabit = { habitId -> 
                        habits.find { it.id == habitId }?.let { viewModel.toggleHabit(it) }
                    },
                    onEntryClick = { /* TODO: Navigate to entry detail */ }
                )
            }

            composable(Screen.Habits.route) {
                HabitsScreen(
                    habits = habits,
                    onToggleHabit = { habitId -> 
                        habits.find { it.id == habitId }?.let { viewModel.toggleHabit(it) }
                    },
                    onAddHabit = { name, description -> 
                        viewModel.addHabit(name, description)
                    }
                )
            }

            composable(Screen.Journal.route) {
                JournalScreen(
                    entries = journalEntries,
                    onEntryClick = { /* TODO: Navigate to entry detail */ },
                    onAddEntry = { title, content -> 
                        viewModel.addJournalEntry(title, content)
                    }
                )
            }

            composable(Screen.Settings.route) {
                SettingsScreen(
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = onThemeToggle
                )
            }
        }
    }
}