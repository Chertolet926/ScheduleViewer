package com.example.schedulerviewer.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.schedulerviewer.ui.screens.ScheduleScreen
import com.example.schedulerviewer.ui.screens.SettingsScreen
import com.example.schedulerviewer.viewmodel.ScheduleViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    widthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Schedule,
        modifier = modifier
    ) {
        composable<Schedule> { backStackEntry ->
            val viewModel: ScheduleViewModel = viewModel(backStackEntry)
            ScheduleScreen( widthSizeClass = widthSizeClass, viewModel = viewModel)
        }

        composable<Settings> {
            SettingsScreen( onBack = { navController.popBackStack() } )
        }
    }
}