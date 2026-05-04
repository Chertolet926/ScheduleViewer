package com.example.schedulerviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.schedulerviewer.ui.navigation.AppNavGraph
import com.example.schedulerviewer.ui.navigation.Settings
import com.example.schedulerviewer.ui.theme.SchedulerViewerTheme

@Composable
internal fun MainScreen(widthSizeClass: WindowWidthSizeClass) {
    val navController = rememberNavController()

    // Observe the current navigation stack to react to destination changes
    val currentDest = navController.currentBackStackEntryAsState().value?.destination

    Scaffold(floatingActionButton = {
        // Only show the FAB if the user is NOT already on the Settings screen
        if (currentDest?.hasRoute<Settings>() != true) {
            FloatingActionButton(
                onClick = { navController.navigate(Settings) { launchSingleTop = true } },
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .size(56.dp),
                containerColor = Color.White.copy(alpha = 0.15f),
                contentColor = Color.White
            ) {
                Icon(
                    contentDescription = stringResource(R.string.settings_description),
                    imageVector = Icons.Default.Settings, modifier = Modifier.size(24.dp)
                )
            }
        }
    }) { padding ->
        // Main navigation host; passes the Scaffold's inner padding to avoid content overlap
        AppNavGraph(navController, widthSizeClass, Modifier.padding(padding))
    }
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // Calculate the window size (Compact, Medium, Expanded) for responsive layouts
            val windowSizeClass = calculateWindowSizeClass(this).widthSizeClass

            // Apply the custom application theme
            SchedulerViewerTheme { MainScreen(windowSizeClass) }
        }
    }
}