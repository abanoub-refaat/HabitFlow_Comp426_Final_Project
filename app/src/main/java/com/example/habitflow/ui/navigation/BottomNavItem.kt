package com.example.habitflow.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.habitflow.R

data class BottomNavItem(
    val screen: Screen,
    val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    @Composable
    fun getLabel(): String = stringResource(labelRes)
}

val bottomNavItems = listOf(
    BottomNavItem(
        screen = Screen.Home,
        labelRes = R.string.nav_home,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    BottomNavItem(
        screen = Screen.Habits,
        labelRes = R.string.nav_habits,
        selectedIcon = Icons.Filled.CheckCircle,
        unselectedIcon = Icons.Outlined.CheckCircle
    ),
    BottomNavItem(
        screen = Screen.Journal,
        labelRes = R.string.nav_journal,
        selectedIcon = Icons.Filled.Create,
        unselectedIcon = Icons.Outlined.Create
    ),
    BottomNavItem(
        screen = Screen.Settings,
        labelRes = R.string.nav_settings,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )
)
