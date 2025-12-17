package com.example.habitflow.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Habits : Screen("habits")
    object Journal : Screen("journal")
    object Settings : Screen("settings")
}
